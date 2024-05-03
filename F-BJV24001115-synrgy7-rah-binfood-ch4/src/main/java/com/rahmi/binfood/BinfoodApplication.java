package com.rahmi.binfood;

import com.rahmi.binfood.controller.MerchantController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BinfoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(BinfoodApplication.class, args);
//		System.out.println("Hello World!");
	}

}
