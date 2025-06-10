package com.extension.project.boundlessbooks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BoundlessBooksApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoundlessBooksApplication.class, args);
    }

}
