package com.db.gerasin.testframework.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Counterparty {
    @Id
    private int id;
    private String name;

}
