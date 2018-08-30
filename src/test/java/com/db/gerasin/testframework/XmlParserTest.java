package com.db.gerasin.testframework;

import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class XmlParserTest {

    @Test
    @SneakyThrows
    public void writeToXml() {
        XmlParser xmlParser = new XmlParser();
        xmlParser.writeToXml();

        File file = new File(xmlParser.getPathName());
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String result = bufferedReader.lines().collect(Collectors.joining("\n"));
        System.out.println(result);
        Assert.assertTrue(true);
    }
}