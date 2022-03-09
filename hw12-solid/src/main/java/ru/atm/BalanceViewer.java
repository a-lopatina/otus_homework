package ru.atm;


class BalanceViewer {

    private final BanknoteStorage banknoteStorage;

    BalanceViewer(BanknoteStorage banknoteStorage){
        this.banknoteStorage = banknoteStorage;
    }

    int getBalance(){
        int amount100 = banknoteStorage.getCell100().getCount() * 100;
        int amount200 = banknoteStorage.getCell200().getCount() * 200;
        int amount500 = banknoteStorage.getCell500().getCount() * 500;
        int amount1000 = banknoteStorage.getCell1000().getCount() * 1000;
        int amount2000 = banknoteStorage.getCell2000().getCount() * 2000;
        int amount5000 = banknoteStorage.getCell5000().getCount() * 5000;
        int storageAmount = amount100 + amount200 + amount500 + amount1000 + amount2000 + amount5000;
        System.out.println("Общая сумма денег в АТМ = " + storageAmount);
        return storageAmount;
    }
}
