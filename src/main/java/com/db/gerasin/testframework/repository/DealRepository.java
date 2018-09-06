package com.db.gerasin.testframework.repository;

import com.db.gerasin.testframework.entity.Deal;
import org.springframework.data.repository.CrudRepository;

public interface DealRepository extends CrudRepository<Deal, Integer> {
}
