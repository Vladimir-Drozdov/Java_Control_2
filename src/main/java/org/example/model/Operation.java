package org.example.model;

import org.example.visitor.ExporterVisitor;
import java.time.LocalDate;

public class Operation {

    public enum Type {
        INCOME, EXPENSE
    }

    private String id;                 // уникальный идентификатор
    private Type type;                 // "INCOME" или "EXPENSE"
    private String bankAccountId;      // id счета
    private double amount;             // сумма операции
    private LocalDate date;            // дата операции
    private String description;        // описание (необязательное)
    private String categoryId;         // id категории

    public Operation(String id, Type type, String bankAccountId,
                     double amount, LocalDate date, String description,
                     String categoryId) {
        this.id = id;
        this.type = type;
        this.bankAccountId = bankAccountId;
        this.amount = amount;
        this.date = date;
        this.description = description != null ? description : "";
        this.categoryId = categoryId;
    }

    // Геттеры
    public String getId() { return id; }
    public Type getType() { return type; }
    public String getBankAccountId() { return bankAccountId; }
    public double getAmount() { return amount; }
    public LocalDate getDate() { return date; }
    public String getDescription() { return description; }
    public String getCategoryId() { return categoryId; }

    // Сеттеры
    public void setType(Type type) { this.type = type; }
    public void setBankAccountId(String bankAccountId) { this.bankAccountId = bankAccountId; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setDescription(String description) { this.description = description; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }

    public void accept(ExporterVisitor v) {
        v.visit(this);
    }
}
