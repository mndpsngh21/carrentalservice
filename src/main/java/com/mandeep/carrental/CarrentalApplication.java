package com.mandeep.carrental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.mandeep.carrental.utils.BootUpManger;

@SpringBootApplication
@ComponentScan({ "com.mandeep.*"})
@EnableScheduling
public class CarrentalApplication implements CommandLineRunner{

	@Autowired 
	BootUpManger bootUpManger;
	
	public static void main(String[] args) {
		SpringApplication.run(CarrentalApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		bootUpManger.init();
	}

}
