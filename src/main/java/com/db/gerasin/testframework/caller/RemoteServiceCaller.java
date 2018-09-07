package com.db.gerasin.testframework.caller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Profile("remote")
public class RemoteServiceCaller implements ServiceCaller {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void call() {
        restTemplate.postForLocation("http://localhost:8081/generate", null);
    }
}
