package com.db.gerasin.testframework.repository;

import com.db.gerasin.testframework.entity.Counterparty;
import org.springframework.data.repository.CrudRepository;

public interface CounterpartyRepository extends CrudRepository<Counterparty, Integer> {
}
