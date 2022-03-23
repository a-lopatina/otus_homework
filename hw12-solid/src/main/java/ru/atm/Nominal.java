package ru.atm;


public enum Nominal {
    NOMINAL_100(100),
    NOMINAL_200(200),
    NOMINAL_500(500),
    NOMINAL_1000(1000),
    NOMINAL_2000(2000),
    NOMINAL_5000(5000);

    private int value;

    Nominal(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
