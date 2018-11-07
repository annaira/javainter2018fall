package org.redischool.fall2018project.usecases.accounting;

import java.util.HashMap;
import java.util.Map;

public class AccountService {

    private Map<String, Account> accounts = new HashMap<>();


    public void createAccount(String id) {
         accounts.put(id, new Account(id)) ;
    }

    public Account getAccount(String id) {
        return accounts.get(id);
    }

    public void deposit(String s, double v) {

    }
}
