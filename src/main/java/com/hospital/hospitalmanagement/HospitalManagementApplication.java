package com.hospital.hospitalmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class HospitalManagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(HospitalManagementApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/admins/**").allowedOrigins("**").allowedHeaders("**");
				registry.addMapping("/bloods/**").allowedOrigins("**").allowedHeaders("**");
				registry.addMapping("/departments/**").allowedOrigins("**").allowedHeaders("**");
				registry.addMapping("/doctors/**").allowedOrigins("**").allowedHeaders("**");
				registry.addMapping("/genders/**").allowedOrigins("**").allowedHeaders("**");
				registry.addMapping("/outpatientCondition/**").allowedOrigins("**").allowedHeaders("**");
				registry.addMapping("/outpatients/**").allowedOrigins("**").allowedHeaders("**");
				registry.addMapping("/patients/**").allowedOrigins("**").allowedHeaders("**");
				registry.addMapping("/roles/**").allowedOrigins("**").allowedHeaders("**");
				registry.addMapping("/users/**").allowedOrigins("**").allowedHeaders("**");
			}
		};
	}

}
