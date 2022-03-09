package ru.atm;

public class BanknoteStorage {
    private BanknoteCell cell100 = new BanknoteCell(Nominal._100, 100);
    private BanknoteCell cell200 = new BanknoteCell(Nominal._200, 100);
    private BanknoteCell cell500 = new BanknoteCell(Nominal._500, 100);
    private BanknoteCell cell1000 = new BanknoteCell(Nominal._1000, 100);
    private BanknoteCell cell2000 = new BanknoteCell(Nominal._2000, 100);
    private BanknoteCell cell5000 = new BanknoteCell(Nominal._5000, 100);

    public BanknoteCell getCell100() {
        return cell100;
    }

    public void setCell100(BanknoteCell cell100) {
        this.cell100 = cell100;
    }

    public BanknoteCell getCell200() {
        return cell200;
    }

    public void setCell200(BanknoteCell cell200) {
        this.cell200 = cell200;
    }

    public BanknoteCell getCell500() {
        return cell500;
    }

    public void setCell500(BanknoteCell cell500) {
        this.cell500 = cell500;
    }

    public BanknoteCell getCell1000() {
        return cell1000;
    }

    public void setCell1000(BanknoteCell cell1000) {
        this.cell1000 = cell1000;
    }

    public BanknoteCell getCell2000() {
        return cell2000;
    }

    public void setCell2000(BanknoteCell cell2000) {
        this.cell2000 = cell2000;
    }

    public BanknoteCell getCell5000() {
        return cell5000;
    }

    public void setCell5000(BanknoteCell cell5000) {
        this.cell5000 = cell5000;
    }
}
