package org.example.resourceserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringOauth2ResourceServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringOauth2ResourceServerApplication.class, args);
	}

}
