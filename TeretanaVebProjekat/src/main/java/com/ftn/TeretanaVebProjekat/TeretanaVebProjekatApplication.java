package com.ftn.TeretanaVebProjekat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TeretanaVebProjekatApplication extends SpringBootServletInitializer {
	  
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TeretanaVebProjekatApplication.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(TeretanaVebProjekatApplication.class, args);
	}
}
