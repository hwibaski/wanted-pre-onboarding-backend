package com.mypost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MyPostApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyPostApplication.class, args);
    }

}
