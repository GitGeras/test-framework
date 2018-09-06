package com.db.gerasin.testframework.mbean;

import com.db.gerasin.testframework.entity.ChangedPerson;
import com.db.gerasin.testframework.entity.Person;
import com.db.gerasin.testframework.parser.CsvParser;
import com.db.gerasin.testframework.parser.XmlParser;
import com.db.gerasin.testframework.repository.ChangedPersonRepository;
import com.db.gerasin.testframework.repository.PersonRepository;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.*;

@Data
@Service
@Slf4j
public class CommandService implements CommandServiceMBean {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ChangedPersonRepository changedPersonRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private XmlParser xmlParser;

    @Autowired
    private CsvParser csvParser;

    @Override
    public void readFromFileToDB() {
        List<Person> people = xmlParser.readFromFile();
        personRepository.saveAll(people);
    }

    @Override
    @SneakyThrows
    public void generateResults() {
        /*Iterable<Person> peopleFromDB = personRepository.findAll();
        List<ChangedPerson> newPeople = StreamSupport.stream(peopleFromDB.spliterator(), false)
                .map(old -> new ChangedPerson(old.getName(), old.getSalary() + 100))
                .collect(Collectors.toList());
        changedPersonRepository.saveAll(newPeople);*/

        ChangedPerson[] newPeople = restTemplate.getForObject("http://localhost:8081/get", ChangedPerson[].class);
        changedPersonRepository.saveAll(Arrays.asList(newPeople));
    }

    @Override
    @SneakyThrows
    public boolean areActualResultsEqualToExpected() {
        Iterator<ChangedPerson> actualResultsIterator = changedPersonRepository.findAll().iterator();

        List<Map<String, String>> expectedResults = csvParser.readFromFile();

        for (Map<String, String> expectedResultMap : expectedResults) {
            ChangedPerson changedPerson = actualResultsIterator.next();
            for (Map.Entry<String, String> entry : expectedResultMap.entrySet()) {
                Field declaredField = changedPerson.getClass().getDeclaredField(entry.getKey());
                declaredField.setAccessible(true);
                Object o = declaredField.get(changedPerson);
                if (!Objects.equals(o.toString(), entry.getValue())) {
                    log.info(changedPerson + " is not equal to " + expectedResultMap);
                    return false;
                }
            }
        }
        return true;
    }
}
