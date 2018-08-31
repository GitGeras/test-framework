package com.db.gerasin.testframework.service;

import com.db.gerasin.testframework.entity.NewPerson;
import com.db.gerasin.testframework.entity.Person;
import com.db.gerasin.testframework.repository.NewPersonRepository;
import com.db.gerasin.testframework.repository.PersonRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Data
@Service
@Slf4j
public class CommandService implements CommandServiceMBean {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private NewPersonRepository newPersonRepository;

    @Autowired
    private XmlParser xmlParser;

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
    public void createNewPeople() {
        Iterable<Person> peopleFromDB = personRepository.findAll();
        List<NewPerson> newPeople = StreamSupport.stream(peopleFromDB.spliterator(), false)
                .map(old -> new NewPerson(old.getName(), old.getSalary() + 100))
                .collect(Collectors.toList());
        newPersonRepository.saveAll(newPeople);
    }

    @Override
    public boolean test() {
        Iterable<Person> peopleFromDB = personRepository.findAll();
        Iterable<NewPerson> newPeopleFromDB = newPersonRepository.findAll();

        Iterator<Person> iter1 = peopleFromDB.iterator();
        Iterator<NewPerson> iter2 = newPeopleFromDB.iterator();

        boolean allRight = true;
        while (iter1.hasNext() && iter2.hasNext() && allRight) {
            allRight = match(iter1.next(), iter2.next());
        }
        return (allRight && !iter1.hasNext() && !iter2.hasNext());
    }

    private boolean match(Person person, NewPerson newPerson) {
        return Objects.equals(person.getName(), newPerson.getName())
                && Objects.equals(person.getSalary() + 100, newPerson.getSalary());
    }
}
