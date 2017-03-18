package com.zzy;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * @author zeus
 * @date 2016年6月11日
 * @version 1.0
 * @description: Tomcat初始化时调用自定义类 spring boot要用tomcat启动
 */
public class ServletInitializer extends SpringBootServletInitializer {
    @Override    
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {  
    	return builder.sources(Main.class);      
    }    
}  
