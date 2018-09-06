package com.db.gerasin.testframework.mbean;

import com.db.gerasin.testframework.entity.ChangedPerson;
import com.db.gerasin.testframework.entity.Person;
import com.db.gerasin.testframework.parser.CsvParser;
import com.db.gerasin.testframework.parser.XmlParser;
import com.db.gerasin.testframework.repository.ChangedPersonRepository;
import com.db.gerasin.testframework.repository.PersonRepository;
import com.db.gerasin.testframework.caller.ServiceCaller;
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
    private PersonRepository personRepository;

    @Autowired
    private ChangedPersonRepository changedPersonRepository;

    @Autowired
    private XmlParser xmlParser;

    @Autowired
    private CsvParser csvParser;

    @Autowired
    private ServiceCaller serviceCaller;

    @Override
    public void readFromFileToDB() {
        List<Person> people = xmlParser.readFromFile();
        personRepository.saveAll(people);
    }

    @Override
    @SneakyThrows
    public void generateResults() {
        serviceCaller.call();
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
