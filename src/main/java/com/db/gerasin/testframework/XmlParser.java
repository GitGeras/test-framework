package com.db.gerasin.testframework;

import com.db.gerasin.testframework.entity.Person;
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
            new Person("John"),
            new Person("Sam"),
            new Person("Andrew"),
            new Person("Bred"));

    public List<Person> readFromFile() throws IOException {
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
        ArrayList<Person> arrayList = mapper.readValue(xml, ArrayList.class);
//        return ((PersonList) xStream.fromXML(xml)).getList();
        return arrayList;
    }

    @SneakyThrows
    public void writeToXml() {
        XmlMapper mapper = new XmlMapper();
        File file = new File(getPathName());
        if (!file.exists()) {
            file.createNewFile();
        }
//        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        log.info("Write to file:");
        mapper.writeValue(file, predefinedPeople);

    }
}


