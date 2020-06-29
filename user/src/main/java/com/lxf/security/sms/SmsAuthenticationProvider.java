package com.lxf.security.sms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author: lxf
 * @create: 2020-06-28 14:55
 * @description: sms ---provider
 */
@Component
public class SmsAuthenticationProvider  implements  AuthenticationProvider{
    @Autowired
    private UserDetailsService userServiceImpl;
    protected final Log logger = LogFactory.getLog(this.getClass());
    private UserDetailsChecker preAuthenticationChecks = new SmsAuthenticationProvider.DefaultPreAuthenticationChecks();
    private UserDetailsChecker postAuthenticationChecks = new SmsAuthenticationProvider.DefaultPostAuthenticationChecks();
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal() == null ? "NONE_PROVIDED" : authentication.getName();
        UserDetails user=null;
        try {
            user = this.retrieveUser(username, (SmsAuthenticationToken)authentication);
        }catch (UsernameNotFoundException var6){
            logger.info(String.format("用户不存在，输入的电话号码为：%s",username));
            throw var6;
        }

        try {
            this.preAuthenticationChecks.check(user);
        } catch (AuthenticationException var7) {
                throw var7;
        }

        this.postAuthenticationChecks.check(user);

        Object principalToReturn = user;
        principalToReturn = user.getUsername();
        return this.createSuccessAuthentication(principalToReturn,authentication, user);
    }

    private Authentication createSuccessAuthentication(Object principalToReturn, Authentication authentication,UserDetails user) {
        SmsAuthenticationToken result = new SmsAuthenticationToken(principalToReturn, user.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }

    private UserDetails retrieveUser(String username, SmsAuthenticationToken authentication) {
        return  userServiceImpl.loadUserByUsername(username);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return SmsAuthenticationToken.class.isAssignableFrom(aClass);
    }
    private class DefaultPreAuthenticationChecks implements UserDetailsChecker {
        private DefaultPreAuthenticationChecks() {
        }

        @Override
        public void check(UserDetails user) {
            if (!user.isAccountNonLocked()) {
                throw new LockedException("账号已锁定");
            } else if (!user.isEnabled()) {
                throw new DisabledException("账号不可用");
            } else if (!user.isAccountNonExpired()) {
                throw new AccountExpiredException("账号已过期");
            }
        }
    }
    private class DefaultPostAuthenticationChecks implements UserDetailsChecker {
        private DefaultPostAuthenticationChecks() {
        }

        @Override
        public void check(UserDetails user) {
            if (!user.isCredentialsNonExpired()) {
                throw new CredentialsExpiredException("凭证已过期");
            }
        }
    }
}
