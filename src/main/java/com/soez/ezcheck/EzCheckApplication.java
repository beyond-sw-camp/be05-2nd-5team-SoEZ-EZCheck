package com.soez.ezcheck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class EzCheckApplication {

    public static void main(String[] args) {
        SpringApplication.run(EzCheckApplication.class, args);
    }

}
