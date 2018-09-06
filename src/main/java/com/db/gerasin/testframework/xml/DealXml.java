package com.db.gerasin.testframework.xml;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class DealXml {
    private int id;
    private int amount;
    private int quantity;
    private char type;
    private int counterpartyId;
    private String counterpartyName;
    private int instrumentId;
    private String instrumentName;
}
