package com.rahmi.binfood;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BinfoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(BinfoodApplication.class, args);
//		System.out.println("Hello World!");
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
