package com.db.gerasin.testframework.service;

import com.db.gerasin.testframework.TestFrameworkApplication;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;

@Service
public class SqlParser {

    private String sourceTableName;
    private String finalTableName;

    private static String getPathName() {
        return TestFrameworkApplication.class.getClassLoader().getResource(".").getFile() + "/data.sql";
    }

    public String getSourceTableName() {
        if (sourceTableName == null) {
            getTableNames();
        }
        return sourceTableName;
    }

    public String getFinalTableName() {
        if (finalTableName == null) {
            getTableNames();
        }
        return finalTableName;
    }

    @SneakyThrows
    private void getTableNames(){
        BufferedReader bufferedReader = new BufferedReader(new FileReader(getPathName()));
        String line = bufferedReader.readLine();
        sourceTableName = line.substring(13, line.indexOf("("));
        line = bufferedReader.readLine();
        finalTableName = line.substring(13, line.indexOf("("));
    }
}
