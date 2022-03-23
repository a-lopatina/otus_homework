package ru.atm;


import java.util.Map;
import java.util.TreeMap;

class CashWithdrawal {

    private final BanknoteStorage banknoteStorage;

    CashWithdrawal(BanknoteStorage banknoteStorage) {
        this.banknoteStorage = banknoteStorage;
    }

    void cashOut(Integer amount) {
        TreeMap<Nominal, Integer> rollbackCopy = new TreeMap<>(banknoteStorage.getBanknotes());
        for (Map.Entry<Nominal, Integer> entry : banknoteStorage.getBanknotes().entrySet()) {
            Integer banknoteCountForCashOut = amount / entry.getKey().getValue();
            if (banknoteCountForCashOut > 0 && entry.getValue() >= banknoteCountForCashOut) {
                amount = amount - (entry.getKey().getValue() * banknoteCountForCashOut);
                Integer currentCount = banknoteStorage.getBanknotes().get(entry.getKey());
                banknoteStorage.getBanknotes().put(entry.getKey(), currentCount - banknoteCountForCashOut);
            }
        }
        if (amount != 0) {
            System.out.println("Нет возможности выдать указанную сумму");
            banknoteStorage.setBanknotes(rollbackCopy);
        }
    }
}
