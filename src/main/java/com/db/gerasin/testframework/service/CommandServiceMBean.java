package com.db.gerasin.testframework.service;

public interface CommandServiceMBean {
    void writeToXml();

    void readFromFileToDB();

    void readFromDB();

    void createNewPeople();

    boolean test();
}
