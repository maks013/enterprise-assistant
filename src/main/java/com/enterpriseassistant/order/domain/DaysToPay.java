package com.enterpriseassistant.order.domain;

public enum DaysToPay {

    SEVEN(7),
    FOURTEEN(14),
    THIRTY(30);

    public final Integer days;

    DaysToPay(Integer days){
        this.days = days;
    }
}
