package com.db.gerasin.testframework.xml;

import com.db.gerasin.testframework.TestFrameworkApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(TestFrameworkApplication.class)
public class XmlParserTest {

    @Autowired
    private XmlParser xmlParser;

    @Autowired
    private XmlGenerator xmlGenerator;

    @Test
    public void xmlNotNullOrEmpty() {
        xmlGenerator.writeToXml();
        List<DealXml> dealXmls = xmlParser.readFromFile();
        assertNotNull(dealXmls);
        assertFalse(dealXmls.isEmpty());
    }
}