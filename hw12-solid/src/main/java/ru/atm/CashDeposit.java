package ru.atm;

import java.util.Map;

class CashDeposit {

     private final BanknoteStorage banknoteStorage;

     CashDeposit(BanknoteStorage banknoteStorage){
          this.banknoteStorage = banknoteStorage;
     }

     void cashIn(Map<Nominal, Integer> banknotes){
          for (Map.Entry<Nominal, Integer> entry : banknotes.entrySet()){
               Integer currentCount = banknoteStorage.getBanknotes().get(entry.getKey());
               banknoteStorage.getBanknotes().put(entry.getKey(), currentCount + entry.getValue());
          }
     }
}
