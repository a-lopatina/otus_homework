package ru.atm;

import java.util.List;

public class Demo {

    // Всего изначально купюр на 880000, вносим наличку на 4500, снимаем 10200, снимаем больше чем есть
    public static void main(String[] args) {
        Atm atm = new Atm();
        atm.getBalance();
        atm.cashIn(List.of(new BanknoteCell(Nominal._200, 5), new BanknoteCell(Nominal._1000, 3), new BanknoteCell(Nominal._500, 1)));
        atm.getBalance();
        atm.cashOut(10200);
        atm.getBalance();
        atm.cashOut(875000);
        atm.getBalance();
    }
}
