package com.db.gerasin.testframework.service;

import com.db.gerasin.testframework.TestFrameworkApplication;
import com.db.gerasin.testframework.entity.Person;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class XmlParser {

    static List<Person> predefinedPeople = Arrays.asList(
            new Person(1, "John", 100),
            new Person(2, "Sam", 300),
            new Person(3, "Andrew", 500),
            new Person(4, "Bred", 1000));

    @SneakyThrows
    public List<Person> readFromFile() {
        File file = new File(getPathName());
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String result = bufferedReader.lines().collect(Collectors.joining("\n"));
        return parseToPersonList(result);
    }

    protected static String getPathName() {
        return TestFrameworkApplication.class.getClassLoader().getResource(".").getFile() + "/test.xml";
    }

    private static List<Person> parseToPersonList(String xml) throws IOException {
        XmlMapper mapper = new XmlMapper();
        ArrayList<Person> arrayList = mapper.readValue(xml, new TypeReference<List<Person>>(){});
//        return ((PersonList) xStream.fromXML(xml)).getList();
        return arrayList;
    }

    @SneakyThrows
    public void writeToXml() {
        XmlMapper mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        File file = new File(getPathName());
        if (!file.exists()) {
            file.createNewFile();
        }
        log.info("Write to file:");
        mapper.writeValue(file, predefinedPeople);

    }
}


