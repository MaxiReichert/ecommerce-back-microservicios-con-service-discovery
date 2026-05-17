package com.tienda.ecommerce.pago;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PagoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PagoApplication.class, args);
	}

}
