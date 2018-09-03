package com.db.gerasin.testframework;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TestFrameworkApplication {

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(TestFrameworkApplication.class, args);
    }
}