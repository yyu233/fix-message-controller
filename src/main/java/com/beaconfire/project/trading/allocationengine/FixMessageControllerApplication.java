package com.beaconfire.project.trading.allocationengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.beaconfire.project.trading")
public class FixMessageControllerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FixMessageControllerApplication.class, args);
	}

}
