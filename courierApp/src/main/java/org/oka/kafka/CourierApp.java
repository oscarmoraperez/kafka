package org.oka.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CourierApp {
    public static void main(String[] args) {
        SpringApplication.run(new Class[]{CourierApp.class}, args);
    }
}
