package com.trainingspringboot.shoppingcart;

import com.trainingspringboot.shoppingcart.utils.filter.LoggingHandler;
import com.trainingspringboot.shoppingcart.utils.filter.MdcInitHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ShoppingCartApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingCartApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer initializerWebMvcConfigurer(MdcInitHandler mdcInitHandler, LoggingHandler loggingHandler) {
		return new WebMvcConfigurer() {
			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				registry.addInterceptor(mdcInitHandler);
				registry.addInterceptor(loggingHandler);
			}
		};
	}
}
