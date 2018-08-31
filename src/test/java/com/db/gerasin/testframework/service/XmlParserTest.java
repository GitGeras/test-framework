package com.db.gerasin.testframework.service;

import com.db.gerasin.testframework.entity.Person;
import com.db.gerasin.testframework.service.XmlParser;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class XmlParserTest {

    private static List<Person> people;

    @BeforeClass
    public static void getPeople() throws IOException {
        XmlParser xmlParser = new XmlParser();
        xmlParser.writeToXml();

        File file = new File(XmlParser.getPathName());
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String result = bufferedReader.lines().collect(Collectors.joining("\n"));
        System.out.println(result);

        people = xmlParser.readFromFile();
    }

    @Test
    @SneakyThrows
    public void writeAndReadXml() {
        System.out.println(people);
        Assert.assertEquals(people.size(), XmlParser.predefinedPeople.size());
    }

    @Test
    @SneakyThrows
    public void isClassRight() {
        Assert.assertEquals(people.get(0).getClass(), Person.class);
    }
}