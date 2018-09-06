package com.db.gerasin.testframework.repository;

import com.db.gerasin.testframework.entity.ChangedPerson;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChangedPersonRepository extends CrudRepository<ChangedPerson, Long> {
    List<ChangedPerson> findByName(String name);
}
