package com.db.gerasin.testframework.service;

import com.db.gerasin.testframework.dao.SourceDao;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Data
@Service
@Slf4j
public class CommandService implements CommandServiceMBean {

    @Autowired
    private SourceDao sourceDao;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private XmlParser xmlParser;

    @Autowired
    private CsvParser csvParser;

    @Autowired
    private SqlParser sqlParser;

    @Override
    public void writeToXml() {
        xmlParser.writeToXml();
    }

    @Override
    public void readFromFileToDB() {
        List<Map<String, String>> source = xmlParser.readFromFile();
        sourceDao.saveAll(sqlParser.getSourceTableName(), source);
    }

    @Override
    public void readFromDB() {
        List<Map<String, String>> list = sourceDao.findAll(sqlParser.getSourceTableName());
        log.info("People found with findAll():");
        log.info("-------------------------------");
        for (Map<String, String> map : list) {
            log.info(map.toString());
        }
    }

    @Override
    @SneakyThrows
    public void createNewPeople() {
        /*Iterable<Person> peopleFromDB = personRepository.findAll();
        List<NewPerson> newPeople = StreamSupport.stream(peopleFromDB.spliterator(), false)
                .map(old -> new NewPerson(old.getName(), old.getSalary() + 100))
                .collect(Collectors.toList());
        newPersonRepository.saveAll(newPeople);*/

//        NewPerson[] newPeople = restTemplate.getForObject("http://localhost:8081/get", NewPerson[].class);
        DataFactory dataFactory = new DataFactory();

        List<Map<String, String>> predefinedPeople = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(i));
            map.put("name", dataFactory.getName());
            Integer salary = Integer.valueOf(dataFactory.getNumberText(4)) + 100;
            map.put("salary", salary.toString());
            predefinedPeople.add(map);
        }

        sourceDao.saveAll(sqlParser.getFinalTableName(), predefinedPeople);
    }

    @Override
    @SneakyThrows
    public boolean test() {
        List<Map<String, String>> actualList = sourceDao.findAll(sqlParser.getFinalTableName());

        List<Map<String, String>> expectedList = csvParser.readFromFile();

        for (int i = 0; i < expectedList.size(); i++) {
            Map<String, String> expectedMap = expectedList.get(i);
            Map<String, String> actualMap = actualList.get(i);
            for (Map.Entry<String, String> expectedEntry : expectedMap.entrySet()) {
                String actualValue = actualMap.get(expectedEntry.getKey());
                if (!Objects.equals(actualValue, expectedEntry.getValue())) {
                    log.info("actual " + actualMap + " is not equal to expected" + expectedMap);
                    return false;
                }
            }
        }
        return true;
    }
}
