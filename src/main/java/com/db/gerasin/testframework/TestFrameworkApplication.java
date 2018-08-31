package com.db.gerasin.testframework;

import com.db.gerasin.testframework.entity.Person;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
@Slf4j
public class TestFrameworkApplication {

    @SneakyThrows
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TestFrameworkApplication.class, args);

        XmlParser xmlParser = context.getBean(XmlParser.class);
        xmlParser.writeToXml();

        List<Person> people = xmlParser.readFromFile();

        PersonRepository repository = context.getBean(PersonRepository.class);

        repository.save(people.get(0));

        Iterable<Person> peopleFromDB = repository.findAll();
        log.info("People found with findAll():");
        log.info("-------------------------------");
        for (Person person : peopleFromDB) {
            log.info(person.toString());
        }
    }
}
