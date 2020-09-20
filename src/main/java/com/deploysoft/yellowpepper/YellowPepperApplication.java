package com.deploysoft.yellowpepper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class YellowPepperApplication {

	public static void main(String[] args) {
		SpringApplication.run(YellowPepperApplication.class, args);
	}

}
