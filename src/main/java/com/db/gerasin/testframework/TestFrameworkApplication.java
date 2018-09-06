package com.db.gerasin.testframework;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class TestFrameworkApplication {

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(TestFrameworkApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public XmlMapper mapper(){
        return new XmlMapper();
    }
}