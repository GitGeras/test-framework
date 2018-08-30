package com.db.gerasin.testframework;

import com.db.gerasin.testframework.entity.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {
    List<Person> findByName(String name);
}
