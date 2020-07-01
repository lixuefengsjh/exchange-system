package com.lxf.config;
import com.lxf.security.image.ImageValidateFilter;
import com.lxf.security.sms.SmsAuthenticationFilter;
import com.lxf.security.sms.SmsAuthenticationProvider;
import com.lxf.security.sms.SmsValidateFilter;
import com.lxf.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @author: lxf
 * @create: 2020-05-13 22:12
 * @description:统一的config配置中心
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private ImageValidateFilter imageValidateFilter;
    @Autowired
    private  UserServiceImpl userServiceImpl;

    @Autowired
    private AuthenticationFailureHandler loginFailedHandler;

    @Autowired
    private AuthenticationSuccessHandler loginSuccessHandler;

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private SmsAuthenticationFilter smsAuthenticationFilter;
    @Autowired
    private SmsAuthenticationProvider smsAuthenticationProvider;
    @Autowired
    private SmsValidateFilter smsValidateFilter;
    @Bean
   public  PersistentTokenRepository  persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository=new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        //tokenRepository.setCreateTableOnStartup(true);
        return  tokenRepository;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                formLogin().loginPage("/login/authentication/required").loginProcessingUrl("/login").
                successHandler(loginSuccessHandler).
                failureHandler(loginFailedHandler).
                    and().
                rememberMe().
                tokenRepository(persistentTokenRepository()).
                userDetailsService(userServiceImpl).
                tokenValiditySeconds(securityProperties.getBrowser().getTokenValiditySeconds()).
                    and().
                authorizeRequests().antMatchers("/page/**",
                "/css/**","/js/**","/layui/**","/images/**","/login/**","/login").permitAll().
                anyRequest().authenticated().
                and().
                csrf().disable();
        http.addFilterBefore(imageValidateFilter, UsernamePasswordAuthenticationFilter.class);
        smsAuthenticationFilter.setAuthenticationFailureHandler(loginFailedHandler);
        smsAuthenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        smsAuthenticationFilter.setAuthenticationManager(http.getSharedObject(ProviderManager.class));
        http.authenticationProvider(smsAuthenticationProvider);
        http.addFilterAfter(smsAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(smsValidateFilter,SmsAuthenticationFilter.class);
    }
    @Override
    public void configure(AuthenticationManagerBuilder bulider) throws Exception {
        bulider.userDetailsService(userServiceImpl).passwordEncoder(passwordEncoder());
    }
    @Bean
    protected PasswordEncoder  passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
