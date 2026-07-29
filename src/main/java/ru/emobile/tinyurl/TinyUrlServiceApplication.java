package ru.emobile.tinyurl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TinyUrlServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TinyUrlServiceApplication.class, args);
    }
}
