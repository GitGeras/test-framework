package com.db.gerasin.testframework.parser;

import com.db.gerasin.testframework.TestFrameworkApplication;
import com.db.gerasin.testframework.entity.Person;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class XmlJacksonParser implements XmlParser {

    @Autowired
    private XmlMapper mapper;

    private static List<Person> predefinedPeople = Arrays.asList(
            new Person(1, "John", 100),
            new Person(2, "Sam", 300),
            new Person(3, "Andrew", 500),
            new Person(4, "Bred", 1000));

    private String xmlPath = TestFrameworkApplication.class.getClassLoader().getResource(".").getFile() + "/test.xml";

    @Override
    @SneakyThrows
    public List<Person> readFromFile() {
        File file = new File(xmlPath);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String result = bufferedReader.lines().collect(Collectors.joining("\n"));
        return parseToPersonList(result);
    }

    private List<Person> parseToPersonList(String xml) throws IOException {
        return mapper.readValue(xml, new TypeReference<List<Person>>(){});
    }

    @Override
    @SneakyThrows
    public void writeToXml() {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        File file = new File(xmlPath);
        if (!file.exists()) {
            file.createNewFile();
        }
        log.info("Write to file:");
        mapper.writeValue(file, predefinedPeople);
    }
}


