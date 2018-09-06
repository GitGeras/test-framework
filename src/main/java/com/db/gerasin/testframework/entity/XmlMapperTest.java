package com.db.gerasin.testframework.entity;

import com.db.gerasin.testframework.TestFrameworkApplication;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.SneakyThrows;
import org.fluttercode.datafactory.impl.DataFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XmlMapperTest {

    private static List<Deal> predefinedPeople = new ArrayList<>();

    private static String xmlPath = TestFrameworkApplication.class.getClassLoader().getResource(".").getFile() + "/test1.xml";

    @SneakyThrows
    public static void main(String[] args) {
        initPredefinedPeople();
        XmlMapper mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        File file = new File(xmlPath);
        if (!file.exists()) {
            file.createNewFile();
        }

        mapper.writeValue(file, DealWrapper.builder().entities(predefinedPeople).build());
    }

    private static void initPredefinedPeople() {
        DataFactory dataFactory = new DataFactory();
        for (int i = 0; i <= 20; i++) {
            predefinedPeople.add(
                    Deal.builder()
                            .id(i + 1)
                            .amount(dataFactory.getNumberBetween(0, 10000))
                            .quantity(dataFactory.getNumberBetween(0, 100))
                            .counterpartyId(i%5 + 1)
                            .counterparty("Counterparty" + i%7 + 1)
                            .instrumentId(i%3 + 1)
                            .instrument("Instrument" + i%3 + 1)
                            .build());
        }
    }
}
