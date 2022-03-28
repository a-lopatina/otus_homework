package ru.atm;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Demo {

    public static void main(String[] args) {
        // первоначальное заполнение банкомата деньгами на 880000
        TreeMap<Nominal, Integer> banknotes = new TreeMap<>(Collections.reverseOrder(Comparator.comparingInt(Nominal::getValue)));
        banknotes.put(Nominal.NOMINAL_100, 100);
        banknotes.put(Nominal.NOMINAL_200, 100);
        banknotes.put(Nominal.NOMINAL_500, 100);
        banknotes.put(Nominal.NOMINAL_1000, 100);
        banknotes.put(Nominal.NOMINAL_2000, 100);
        banknotes.put(Nominal.NOMINAL_5000, 100);
        BanknoteStorage banknoteStorage = new BanknoteStorage(banknotes);
        AtmImpl atm = new AtmImpl(banknoteStorage);

        // смотрим баланс
        atm.getBalance();

        // вносим 4500 и смотрим баланс
        Map<Nominal, Integer> banknotesForCashIn = new HashMap<>();
        banknotesForCashIn.put(Nominal.NOMINAL_200, 5);
        banknotesForCashIn.put(Nominal.NOMINAL_1000, 3);
        banknotesForCashIn.put(Nominal.NOMINAL_500, 1);
        atm.cashIn(banknotesForCashIn);
        atm.getBalance();

        // снимаем 10200, проверяем баланс
        atm.cashOut(10200);
        atm.getBalance();

        // снимаем больше чем есть и смотрим баланс
        atm.cashOut(875000);
        atm.getBalance();
    }
}
