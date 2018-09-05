package com.db.gerasin.testframework.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class XmlParserTest {


//    @BeforeClass
    public static void getPeople() throws IOException {
        XmlParser xmlParser = new XmlParser();
        xmlParser.writeToXml();

        File file = new File(XmlParser.getPathName());
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String result = bufferedReader.lines().collect(Collectors.joining("\n"));
        System.out.println(result);

        List<Map<String, String>> maps = xmlParser.readFromFile();
    }
}