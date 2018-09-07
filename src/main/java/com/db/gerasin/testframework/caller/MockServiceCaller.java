package com.db.gerasin.testframework.caller;

import com.db.gerasin.testframework.entity.Counterparty;
import com.db.gerasin.testframework.entity.Deal;
import com.db.gerasin.testframework.entity.Result;
import com.db.gerasin.testframework.repository.CounterpartyRepository;
import com.db.gerasin.testframework.repository.DealRepository;
import com.db.gerasin.testframework.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Profile("mock")
public class MockServiceCaller implements ServiceCaller {

    @Autowired
    private CounterpartyRepository counterpartyRepository;

    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Override
    public void call() {
        Iterable<Counterparty> counterparties = counterpartyRepository.findAll();
        Map<Integer, String> counterpartyMap = StreamSupport
                .stream(counterparties.spliterator(), false)
                .collect(Collectors.toMap(Counterparty::getId, Counterparty::getName));

        Iterable<Deal> deals = dealRepository.findAll();
        Map<Integer, List<Deal>> dealsByCounterparties = StreamSupport
                .stream(deals.spliterator(), false)
                .collect(Collectors.groupingBy(Deal::getCounterpartyId));

        Set<Result> results = new HashSet<>();

        for (Map.Entry<Integer, List<Deal>> entry : dealsByCounterparties.entrySet()) {
            results.add(new Result(entry.getKey(), counterpartyMap.get(entry.getKey()), entry.getValue().size()));
        }

        resultRepository.saveAll(results);
    }
}
