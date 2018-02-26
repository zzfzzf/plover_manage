<<<<<<< HEAD
package com.zzy.Interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * Created by Administrator on 2017/3/26.
 */
@Configuration
public class InterceptorConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        InterceptorRegistration ir = registry.addInterceptor(new PowerInterceptor());
        // 配置拦截的路径
        ir.addPathPatterns(new String[]{"/**"});
        // 配置不拦截的路径
        ir.excludePathPatterns(new String[]{"/get/topImage","/login","/all/grid","/register","/forget/password","/logout","/reset/password"});

        // 还可以在这里注册其它的拦截器
        //registry.addInterceptor(new OtherInterceptor()).addPathPatterns("/**");
    }
=======
package com.zzy.Interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * Created by Administrator on 2017/3/26.
 */
@Configuration
public class InterceptorConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        InterceptorRegistration ir = registry.addInterceptor(new PowerInterceptor());
        // 配置拦截的路径
        ir.addPathPatterns(new String[]{"/**"});
        // 配置不拦截的路径
        ir.excludePathPatterns(new String[]{"/get/topImage","/login","/all/grid","/register","/forget/password","/logout","/reset/password"});

        // 还可以在这里注册其它的拦截器
        //registry.addInterceptor(new OtherInterceptor()).addPathPatterns("/**");
    }
>>>>>>> 87862a9414168c7958e3f27eded4d0e8c1646de5
}