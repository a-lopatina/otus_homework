package ru.atm;


public enum Nominal {
    _100(100),
    _200(200),
    _500(500),
    _1000(1000),
    _2000(2000),
    _5000(5000);

    private int value;

    Nominal(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
