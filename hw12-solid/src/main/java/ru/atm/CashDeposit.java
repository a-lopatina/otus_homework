package ru.atm;

import java.util.List;

class CashDeposit {

     private final BanknoteStorage banknoteStorage;

     CashDeposit(BanknoteStorage banknoteStorage){
          this.banknoteStorage = banknoteStorage;
     }

     void cashIn(List<BanknoteCell> banknotes){
          for (BanknoteCell banknote : banknotes){
               switch (banknote.getNominal()){
                    case _100 -> banknoteStorage.getCell100().increase(banknote.getCount());
                    case _200 -> banknoteStorage.getCell200().increase(banknote.getCount());
                    case _500 -> banknoteStorage.getCell500().increase(banknote.getCount());
                    case _1000 -> banknoteStorage.getCell1000().increase(banknote.getCount());
                    case _2000 -> banknoteStorage.getCell2000().increase(banknote.getCount());
                    case _5000 -> banknoteStorage.getCell5000().increase(banknote.getCount());
               }
          }
     }
}
