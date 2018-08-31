package com.db.gerasin.testframework.repository;

import com.db.gerasin.testframework.entity.NewPerson;
import com.db.gerasin.testframework.entity.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NewPersonRepository extends CrudRepository<NewPerson, Long> {
    List<NewPerson> findByName(String name);
}
