package ru.atm;


import java.util.TreeMap;

public class BanknoteStorage {

    private TreeMap<Nominal, Integer> banknotes;

    BanknoteStorage(TreeMap<Nominal, Integer> banknotes) {
        this.banknotes = banknotes;
    }

    public TreeMap<Nominal, Integer> getBanknotes() {
        return banknotes;
    }

    public void setBanknotes(TreeMap<Nominal, Integer> banknotes) {
        this.banknotes = banknotes;
    }
}
