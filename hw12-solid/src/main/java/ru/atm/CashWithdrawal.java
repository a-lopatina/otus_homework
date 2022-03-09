package ru.atm;


class CashWithdrawal {

    private final BanknoteStorage banknoteStorage;

    CashWithdrawal(BanknoteStorage banknoteStorage) {
        this.banknoteStorage = banknoteStorage;
    }

    void cashOut(int amount) {
        int _5000BanknoteCount = amount / Nominal._5000.getValue();
        if (_5000BanknoteCount > 0 && banknoteStorage.getCell5000().getCount() >= _5000BanknoteCount) {
            amount = amount - (Nominal._5000.getValue() * _5000BanknoteCount);
        }
        int _2000BanknoteCount = amount / Nominal._2000.getValue();
        if (_2000BanknoteCount > 0 && banknoteStorage.getCell2000().getCount() >= _2000BanknoteCount) {
            amount = amount - (Nominal._2000.getValue() * _2000BanknoteCount);
        }
        int _1000BanknoteCount = amount / Nominal._1000.getValue();
        if (_1000BanknoteCount > 0 && banknoteStorage.getCell1000().getCount() >= _1000BanknoteCount) {
            amount = amount - (Nominal._1000.getValue() * _1000BanknoteCount);
        }
        int _500BanknoteCount = amount / Nominal._500.getValue();
        if (_500BanknoteCount > 0 && banknoteStorage.getCell500().getCount() >= _500BanknoteCount) {
            amount = amount - (Nominal._500.getValue() * _500BanknoteCount);
        }
        int _200BanknoteCount = amount / Nominal._200.getValue();
        if (_200BanknoteCount > 0 && banknoteStorage.getCell200().getCount() >= _200BanknoteCount) {
            amount = amount - (Nominal._200.getValue() * _200BanknoteCount);
        }
        int _100BanknoteCount = amount / Nominal._100.getValue();
        if (_100BanknoteCount > 0 && banknoteStorage.getCell200().getCount() >= _100BanknoteCount) {
            amount = amount - (Nominal._100.getValue() * _100BanknoteCount);
        }
        if (amount != 0) {
            System.out.println("Нет возможности выдать указанную сумму");
        } else {
            banknoteStorage.getCell5000().decrease(_5000BanknoteCount);
            banknoteStorage.getCell2000().decrease(_2000BanknoteCount);
            banknoteStorage.getCell1000().decrease(_1000BanknoteCount);
            banknoteStorage.getCell500().decrease(_500BanknoteCount);
            banknoteStorage.getCell200().decrease(_200BanknoteCount);
            banknoteStorage.getCell100().decrease(_100BanknoteCount);
        }
    }
}
