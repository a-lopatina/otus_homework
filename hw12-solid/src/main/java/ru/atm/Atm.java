package ru.atm;

import java.util.List;

public class Atm {
    private final BanknoteStorage banknoteStorage = new BanknoteStorage();

    void cashIn(List<BanknoteCell> banknotes){
        CashDeposit cashDeposit = new CashDeposit(banknoteStorage);
        cashDeposit.cashIn(banknotes);
    }

    void cashOut(int amount){
        CashWithdrawal cashWithdrawal = new CashWithdrawal(banknoteStorage);
        cashWithdrawal.cashOut(amount);
    }

    int getBalance(){
        BalanceViewer balanceViewer = new BalanceViewer(banknoteStorage);
        return balanceViewer.getBalance();
    }

}
