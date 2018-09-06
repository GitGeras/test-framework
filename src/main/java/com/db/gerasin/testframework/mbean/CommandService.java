package com.db.gerasin.testframework.mbean;

import com.db.gerasin.testframework.caller.ServiceCaller;
import com.db.gerasin.testframework.entity.Result;
import com.db.gerasin.testframework.csv.CsvParser;
import com.db.gerasin.testframework.repository.DbService;
import com.db.gerasin.testframework.xml.DealXml;
import com.db.gerasin.testframework.xml.XmlParser;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@Service
@Slf4j
public class CommandService implements CommandServiceMBean {

    @Autowired
    private DbService dbService;

    @Autowired
    private XmlParser xmlParser;

    @Autowired
    private CsvParser csvParser;

    @Autowired
    private ServiceCaller serviceCaller;

    @Override
    public void readFromFileToDB() {
        List<DealXml> deals = xmlParser.readFromFile();
        dbService.saveAll(deals);
    }

    @Override
    @SneakyThrows
    public void generateResults() {
        serviceCaller.call();
    }

    @Override
    @SneakyThrows
    public boolean areActualResultsEqualToExpected() {
        Iterator<Result> actualResultsIterator = dbService.getResults().iterator();

        List<Map<String, String>> expectedResults = csvParser.readFromFile();

        for (Map<String, String> expectedResultMap : expectedResults) {
            Result result = actualResultsIterator.next();
            for (Map.Entry<String, String> entry : expectedResultMap.entrySet()) {
                Field declaredField = result.getClass().getDeclaredField(entry.getKey());
                declaredField.setAccessible(true);
                Object o = declaredField.get(result);
                if (!Objects.equals(o.toString(), entry.getValue())) {
                    log.info(result + " is not equal to " + expectedResultMap);
                    return false;
                }
            }
        }
        return true;
    }
}
