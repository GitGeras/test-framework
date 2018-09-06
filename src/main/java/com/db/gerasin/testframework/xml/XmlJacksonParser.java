package com.db.gerasin.testframework.xml;

import com.db.gerasin.testframework.TestFrameworkApplication;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class XmlJacksonParser implements XmlParser {

    @Autowired
    private XmlMapper mapper;

    private String xmlPath = TestFrameworkApplication.class.getClassLoader().getResource(".").getFile() + "/test.xml";

    @Override
    @SneakyThrows
    public List<DealXml> readFromFile() {
        File file = new File(xmlPath);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String result = bufferedReader.lines().collect(Collectors.joining("\n"));
        return parseToDealXmlList(result);
    }

    private List<DealXml> parseToDealXmlList(String xml) throws IOException {
        return mapper.readValue(xml, DealXmlWrapper.class).getEntities();
    }
}


