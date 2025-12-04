package org.example.factory;

import org.example.model.BankAccount;
import org.example.model.Category;
import org.example.model.Operation;

import java.time.LocalDate;
//Фабрика — это шаблон для создания объектов.
//Когда надо создавать объекты определённого типа, но не хочешь светить детали того, как они создаются. Внешний код просто говорит: «дай объект», а фабрика уже решает, какой именно конструктор использовать, какие параметры подставить, какие проверки выполнить.
public class DomainFactory {

    public BankAccount createAccount(String id, String name, double balance) {
        if (balance < 0) throw new IllegalArgumentException("negative balance");
        return new BankAccount(id, name, balance);
    }

    public Category createCategory(String id, Category.Type type, String name) {
        return new Category(id, type, name);
    }

    public Operation createOperation(String id, Operation.Type type, String accId, double amount,
                                     LocalDate date, String desc, String catId) {
        if (amount <= 0) throw new IllegalArgumentException("invalid amount");
        return new Operation(id, type, accId, amount, date, desc, catId);
    }
}
