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


//
// 1. Валидация XMl
// 2. Возможность генерации схем из скрипта.
// 3. Сгенерить энтити классы по sql query.
// 4. Выводить что не так, если тест свалился.