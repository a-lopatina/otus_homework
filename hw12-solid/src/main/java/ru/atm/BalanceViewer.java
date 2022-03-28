package ru.atm;



class BalanceViewer {

    private final BanknoteStorage banknoteStorage;

    BalanceViewer(BanknoteStorage banknoteStorage) {
        this.banknoteStorage = banknoteStorage;
    }

    Integer getBalance() {
        int storageAmount = banknoteStorage.getBanknotes().entrySet().stream()
                .mapToInt(banknoteCell -> banknoteCell.getValue() * banknoteCell.getKey().getValue())
                .sum();
        System.out.println("Общая сумма денег в АТМ = " + storageAmount);
        return storageAmount;
    }
}
