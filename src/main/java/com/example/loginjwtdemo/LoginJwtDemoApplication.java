package com.example.loginjwtdemo;

import com.example.loginjwtdemo.filter.LoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LoginJwtDemoApplication {

	@Autowired
	private LoginFilter loginFilter;

	@Bean
	public FilterRegistrationBean loginFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(loginFilter);
		registration.addUrlPatterns("/home");
		registration.setOrder(1);
		return registration;
	}

	public static void main(String[] args) {
		SpringApplication.run(LoginJwtDemoApplication.class, args);
	}
}
