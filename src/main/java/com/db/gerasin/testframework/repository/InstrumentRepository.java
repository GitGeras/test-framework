package com.db.gerasin.testframework.repository;

import com.db.gerasin.testframework.entity.Instrument;
import org.springframework.data.repository.CrudRepository;

public interface InstrumentRepository extends CrudRepository<Instrument, Integer> {
}
