package org.example.model;

import org.example.visitor.ExporterVisitor;

public class BankAccount {

    private final String id;      // уникальный идентификатор
    private String name;    // название счета
    private double balance;       // баланс

    public BankAccount(String id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name=name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void accept(ExporterVisitor v) {
        v.visit(this);
    }
}
