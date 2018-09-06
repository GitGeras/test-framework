package com.db.gerasin.testframework.mbean;

public interface CommandServiceMBean {
    void readFromFileToDB();

    void generateResults();

    boolean areActualResultsEqualToExpected();
}
