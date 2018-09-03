package com.db.gerasin.testframework.repository;

import com.db.gerasin.testframework.entity.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Integer> {
    List<Person> findByName(String name);
}
