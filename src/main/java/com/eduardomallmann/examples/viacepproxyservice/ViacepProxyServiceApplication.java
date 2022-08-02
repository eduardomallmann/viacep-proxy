package com.eduardomallmann.examples.viacepproxyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ViacepProxyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ViacepProxyServiceApplication.class, args);
	}
}
