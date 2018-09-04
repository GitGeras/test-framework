package com.db.gerasin.testframework.service;

import com.db.gerasin.testframework.entity.NewPerson;
import com.db.gerasin.testframework.entity.Person;
import com.db.gerasin.testframework.repository.NewPersonRepository;
import com.db.gerasin.testframework.repository.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private NewPersonRepository newPersonRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private XmlParser xmlParser;

    @Autowired
    private CsvParser csvParser;

    @Override
    public void writeToXml() {
        xmlParser.writeToXml();
    }

    @Override
    public void readFromFileToDB() {
        List<Person> people = xmlParser.readFromFile();
        personRepository.saveAll(people);
    }

    @Override
    public void readFromDB() {
        Iterable<Person> peopleFromDB = personRepository.findAll();
        log.info("People found with findAll():");
        log.info("-------------------------------");
        for (Person person : peopleFromDB) {
            log.info(person.toString());
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

        NewPerson[] newPeople = restTemplate.getForObject("http://localhost:8081/get", NewPerson[].class);
        newPersonRepository.saveAll(Arrays.asList(newPeople));
    }

    @Override
    @SneakyThrows
    public boolean test() {
        Iterable<NewPerson> newPeopleFromDB = newPersonRepository.findAll();
        Iterator<NewPerson> iter2 = newPeopleFromDB.iterator();

        List<Map<String, String>> mapList = csvParser.readFromFile();

        for (Map<String, String> map : mapList) {
            NewPerson newPerson = iter2.next();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                Field declaredField = newPerson.getClass().getDeclaredField(entry.getKey());
                declaredField.setAccessible(true);
                Object o = declaredField.get(newPerson);
                if (!Objects.equals(o.toString(), entry.getValue())) {
                    log.info(newPerson + " is not equal to " + map);
                    return false;
                }
            }
        }
        return true;
    }

    private boolean match(Person person, NewPerson newPerson) {
        return Objects.equals(person.getName(), newPerson.getName())
                && Objects.equals(person.getSalary() + 100, newPerson.getSalary());
    }
}
