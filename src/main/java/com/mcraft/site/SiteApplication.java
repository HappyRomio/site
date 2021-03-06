package com.mcraft.site;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;



@SpringBootApplication
@EntityScan(basePackages = {"entity"})

public class SiteApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SiteApplication.class, args);
		
	
	}

}
