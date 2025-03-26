package com.example.securingweb;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
/*
 * @EnableWebMvc-annotated configuration classes may implement this interface
 * to be called back and given a chance to customize the default configuration.
 */
public class MvcConfig implements WebMvcConfigurer {
    
       @Override//​Java 语法规范：覆盖父类或接口中的方法时，@Override增强代码可读性，并让编译器检查方法签名是否正确。
       public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/hello").setViewName("hello");
		registry.addViewController("/login").setViewName("login");
	}


}
