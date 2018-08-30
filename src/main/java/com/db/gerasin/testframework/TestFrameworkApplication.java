package com.db.gerasin.testframework;

import com.db.gerasin.testframework.entity.Person;
import com.db.gerasin.testframework.entity.PersonList;
import com.thoughtworks.xstream.XStream;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class TestFrameworkApplication {

    private static final Logger log = LoggerFactory.getLogger(TestFrameworkApplication.class);
    private static XStream xStream;

    private static void configureXstream() {
        xStream = new XStream();
        xStream.alias("person", Person.class);
        xStream.alias("people", PersonList.class);
        xStream.addImplicitCollection(PersonList.class, "list", Person.class);
    }

    @SneakyThrows
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TestFrameworkApplication.class, args);
        configureXstream();

        List<Person> predefinedPeople = Arrays.asList(new Person("John"), new Person("Sam"), new Person("Andrew"), new Person("Bred"));

        String xml = getAsXml(predefinedPeople);

        writeToFile(xml);
        List<Person> people = readFromFile();

//        PersonRepository repository = context.getBean(PersonRepository.class);

//        Iterable<Person> people = repository.findAll();
        log.info("People found with findAll():");
        log.info("-------------------------------");
        for (Person person : people) {
            log.info(person.toString());
        }
    }

    private static List<Person> readFromFile() throws FileNotFoundException {
        File file = new File(getPathName());
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String result = bufferedReader.lines().collect(Collectors.joining("\n"));
        return parseToPersonList(result);
    }

    private static void writeToFile(String xml) throws IOException {
        File file = new File(getPathName());
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        log.info("Write to file:");
        writer.write(xml);
        writer.close();
        log.info(xml);
    }

    private static String getPathName() {
        return TestFrameworkApplication.class.getClassLoader().getResource(".").getFile() + "/test.xml";
    }

    private static String getAsXml(List<Person> persons) {
        return xStream.toXML(new PersonList(persons));
    }


    private static List<Person> parseToPersonList(String xml) {
        return ((PersonList) xStream.fromXML(xml)).getList();
    }
}
