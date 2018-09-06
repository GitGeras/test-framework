package com.db.gerasin.testframework.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Result {
    @Id
    private int counterpartyId;
    private String counterpartyName;
    private int dealsQty;

}
