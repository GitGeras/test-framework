package com.db.gerasin.testframework.xml;

import com.db.gerasin.testframework.TestFrameworkApplication;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class XmlGenerator {

    @Autowired
    private XmlMapper mapper = new XmlMapper();

    private List<DealXml> predefinedEntities = new ArrayList<>();

    private String xmlPath = TestFrameworkApplication.class.getClassLoader().getResource(".").getFile() + "/test.xml";

    @SneakyThrows
    public void writeToXml() {
        initPredefinedPeople();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        File file = new File(xmlPath);
        if (!file.exists()) {
            file.createNewFile();
        }
        log.info("Write to file");
        mapper.writeValue(file, new DealXmlWrapper(predefinedEntities));
    }

    private void initPredefinedPeople() {
        DataFactory dataFactory = new DataFactory();
        for (int i = 0; i < 20; i++) {
            predefinedEntities.add(
                    DealXml.builder()
                            .id(i + 1)
                            .amount(dataFactory.getNumberBetween(0, 10000))
                            .quantity(dataFactory.getNumberBetween(0, 100))
                            .type(i%2 == 0 ? 'B' : 'S')
                            .counterpartyId(i % 5 + 1)
                            .counterpartyName("Counterparty" + (i % 5 + 1))
                            .instrumentId(i % 3 + 1)
                            .instrumentName("Instrument" + (i % 3 + 1))
                            .build());
        }
    }

    public static void main(String[] args) {
        new XmlGenerator().writeToXml();
    }
}
