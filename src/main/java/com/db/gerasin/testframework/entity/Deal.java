package com.db.gerasin.testframework.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Deal {
    private int id;
    private int amount;
    private int quantity;
    private int counterpartyId;
    private String counterparty;
    private int instrumentId;
    private String instrument;
}
