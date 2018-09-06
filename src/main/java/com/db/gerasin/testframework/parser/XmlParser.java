package com.db.gerasin.testframework.parser;

import com.db.gerasin.testframework.entity.Person;
import lombok.SneakyThrows;

import java.util.List;

public interface XmlParser {
    @SneakyThrows
    List<Person> readFromFile();

    @SneakyThrows
    void writeToXml();
}
