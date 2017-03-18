package com.zzy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

/** 
* @author zeus
* @version 1.0.0
* @date:2016年3月31日 
* @description: 微服务入口
* @Configuration @EnableAutoConfiguration @ComponentScan ====== @springBootApplication
* @EnableCaching 开启缓存
*/
// 注意不要忘记添加 @ServletComponentScan注解，不然找不到servlet和filter报错404
// 使用swagger的时候访问localhost:8080/swagger-ui.html
// 使用druid的时候访问localhost:8080/druid/index.html 
@ServletComponentScan 
@EnableCaching
@SpringBootApplication 
public class Main {
	public static void main(String[] args) throws Exception {
		// Close the context so it doesn't stay awake listening for redis
		SpringApplication.run(Main.class, args); 
	}
}
