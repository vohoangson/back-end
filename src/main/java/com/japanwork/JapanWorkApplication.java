package com.japanwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.japanwork.config.AppProperties;

@SpringBootApplication
@EnableWebMvc
@EnableConfigurationProperties(AppProperties.class)
public class JapanWorkApplication {
	public static void main(String[] args) {		
		ApplicationContext ctx = SpringApplication.run(JapanWorkApplication.class, args);

        DispatcherServlet dispatcherServlet = (DispatcherServlet)ctx.getBean("dispatcherServlet");
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
	}
}
