package com.db.gerasin.testframework.repository;

import com.db.gerasin.testframework.entity.Counterparty;
import com.db.gerasin.testframework.entity.Deal;
import com.db.gerasin.testframework.entity.Instrument;
import com.db.gerasin.testframework.entity.Result;
import com.db.gerasin.testframework.xml.DealXml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DbService {

    @Autowired
    private CounterpartyRepository counterpartyRepository;

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Transactional
    public void saveAll(List<DealXml> dealsXml) {
        saveCounterparties(dealsXml);
        saveInstruments(dealsXml);
        saveDeals(dealsXml);
    }

    private void saveCounterparties(List<DealXml> dealsXml) {
        Set<Counterparty> counterparties = dealsXml.stream()
                .map(dealXml -> new Counterparty(dealXml.getCounterpartyId(), dealXml.getCounterpartyName()))
                .collect(Collectors.toSet());
        counterpartyRepository.saveAll(counterparties);
    }

    private void saveInstruments(List<DealXml> dealsXml) {
        Set<Instrument> instruments = dealsXml.stream()
                .map(dealXml -> new Instrument(dealXml.getInstrumentId(), dealXml.getInstrumentName()))
                .collect(Collectors.toSet());
        instrumentRepository.saveAll(instruments);
    }

    private void saveDeals(List<DealXml> dealsXml) {
        Set<Deal> deals = dealsXml.stream()
                .map(DbService::dealXmlToEntity)
                .collect(Collectors.toSet());
        dealRepository.saveAll(deals);
    }

    private static Deal dealXmlToEntity(DealXml dealXml) {
        return Deal.builder()
                .id(dealXml.getId())
                .amount(dealXml.getAmount())
                .quantity(dealXml.getQuantity())
                .type(dealXml.getType())
                .counterpartyId(dealXml.getCounterpartyId())
                .instrumentId(dealXml.getInstrumentId())
                .build();
    }

    public Iterable<Result> getResults(){
        return resultRepository.findAll();
    }
}
