package com.db.gerasin.testframework;

import com.db.gerasin.testframework.entity.Person;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class XmlParserTest {

    @Test
    @SneakyThrows
    public void writeAndReadXml() {
        XmlParser xmlParser = new XmlParser();
        xmlParser.writeToXml();

        File file = new File(XmlParser.getPathName());
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String result = bufferedReader.lines().collect(Collectors.joining("\n"));
        System.out.println(result);

        List<Person> people = xmlParser.readFromFile();
        System.out.println(people);
        Assert.assertEquals(people.size(), XmlParser.predefinedPeople.size());
    }
}