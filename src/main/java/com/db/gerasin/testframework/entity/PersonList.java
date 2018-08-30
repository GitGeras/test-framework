package com.db.gerasin.testframework.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class PersonList {

    @Getter
    private List<Person> list = new ArrayList<>();

    public PersonList(List<Person> list) {
        this.list = list;
    }
}