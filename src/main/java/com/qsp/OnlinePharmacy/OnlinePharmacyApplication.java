package com.qsp.OnlinePharmacy;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlinePharmacyApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlinePharmacyApplication.class, args);
	}
	
	@Bean
	public ModelMapper getMapper()
	{
		return new ModelMapper();
	}
}
