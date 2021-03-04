package com.example.contractmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.contractmanagement"})
public class ContractManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContractManagementApplication.class, args);
	}

}
