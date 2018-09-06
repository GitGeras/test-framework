package com.db.gerasin.testframework.caller;

import com.db.gerasin.testframework.entity.ChangedPerson;
import com.db.gerasin.testframework.repository.ChangedPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class RemoteServiceCaller implements ServiceCaller {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ChangedPersonRepository changedPersonRepository;

    @Override
    public void call() {
        /*Iterable<Person> peopleFromDB = personRepository.findAll();
        List<ChangedPerson> newPeople = StreamSupport.stream(peopleFromDB.spliterator(), false)
                .map(old -> new ChangedPerson(old.getName(), old.getSalary() + 100))
                .collect(Collectors.toList());
        changedPersonRepository.saveAll(newPeople);*/

        ChangedPerson[] newPeople = restTemplate.getForObject("http://localhost:8081/get", ChangedPerson[].class);
        changedPersonRepository.saveAll(Arrays.asList(newPeople));
    }
}
