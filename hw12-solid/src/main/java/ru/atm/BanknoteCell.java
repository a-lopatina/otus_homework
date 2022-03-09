package ru.atm;

public class BanknoteCell {
    private Nominal nominal;
    private int count;

    public BanknoteCell(Nominal nominal, int count) {
        this.nominal = nominal;
        this.count = count;
    }

    public Nominal getNominal() {
        return nominal;
    }

    public int getCount() {
        return count;
    }

    public void setNominal(Nominal nominal) {
        this.nominal = nominal;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void increase(int increaseCount) {
        this.count += increaseCount;
    }

    public void decrease(int decreaseCount) {
        this.count -= decreaseCount;
    }
}
