package org.redischool.fall2018project.usecases.accounting;

import java.util.Objects;

public class Account {

    private String id;

    public Account(String id){
        this.id = id;
    }


    public double getBalance() {
        return 0.0;
    }

    public String getId() {
        return  id;
    }
}
