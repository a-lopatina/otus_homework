package ru.atm;

import java.util.Map;

public class AtmImpl implements Atm{
    private final BanknoteStorage banknoteStorage;

    public AtmImpl(BanknoteStorage banknoteStorage){
        this.banknoteStorage = banknoteStorage;
    }

    @Override
    public void cashIn(Map<Nominal, Integer> banknotes){
        CashDeposit cashDeposit = new CashDeposit(banknoteStorage);
        cashDeposit.cashIn(banknotes);
    }

    @Override
    public void cashOut(Integer amount){
        CashWithdrawal cashWithdrawal = new CashWithdrawal(banknoteStorage);
        cashWithdrawal.cashOut(amount);
    }

    @Override
    public Integer getBalance(){
        BalanceViewer balanceViewer = new BalanceViewer(banknoteStorage);
        return balanceViewer.getBalance();
    }

}
