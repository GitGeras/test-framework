package com.db.gerasin.testframework.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
@NoArgsConstructor
@ToString
public class NewPerson {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    private int salary;

    public NewPerson(String name, int salary) {
        this.name = name;
        this.salary = salary;
    }
}