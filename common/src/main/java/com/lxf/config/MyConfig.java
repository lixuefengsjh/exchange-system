//package com.lxf.config;
//
//import com.lxf.Filter.TimeFilter;
//import com.lxf.interceptor.ExpandInterceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author: lxf
// * @create: 2020-04-21 22:00
// * @description:
// */
//@Configuration
//public class MyConfig  implements WebMvcConfigurer {
//    @Autowired
//    private ExpandInterceptor expandInterceptor;
//    /**
//     *(1)对于Filter,可以在自定义的Filter上加上@Compent注解。可以对所有的controller进行拦截
//     * (2)也可以通过FilterRegistrationBean进行管理
//     * @return
//     */
//    @Bean
//    public FilterRegistrationBean getBean(){
//
//        FilterRegistrationBean filterRegister=new FilterRegistrationBean();
//        TimeFilter  timeFilter =new TimeFilter();
//        List<String> urls=new ArrayList<>();
//        urls.add("/*");
//        filterRegister.setUrlPatterns(urls);
//        filterRegister.setFilter(timeFilter);
//        return filterRegister;
//    }
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        /**
//         * (1)config中继承WebMvcConfigure
//         * 可以定义多个拦截器，并定义拦截器的执行顺序
//         */
//        InterceptorRegistration reg=registry.addInterceptor(expandInterceptor);
//        reg.order(1);
//        List<String> urls=new ArrayList<>();
//        urls.add("/**");
//        reg.addPathPatterns(urls);
//    }
//}
