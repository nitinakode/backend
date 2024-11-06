package com.exemple.demo_product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class DemoProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoProductApplication.class, args);
	}

}
