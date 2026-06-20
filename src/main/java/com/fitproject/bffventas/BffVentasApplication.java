package com.fitproject.bffventas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BffVentasApplication {
    public static void main(String[] args) {
        SpringApplication.run(BffVentasApplication.class, args);
    }
}