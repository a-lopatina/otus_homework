package ru.atm;

import java.util.Map;

public interface Atm {

    void cashIn(Map<Nominal, Integer> banknotes);

    void cashOut(Integer amount);

    Integer getBalance();
}
