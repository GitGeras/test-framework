package com.db.gerasin.testframework.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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