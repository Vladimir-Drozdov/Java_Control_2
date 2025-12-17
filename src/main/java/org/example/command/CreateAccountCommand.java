package org.example.command;

import org.example.facade.BankAccountFacade;
//Комманда - поведенческий паттерн, цель инкапсулировтаь действие как объект, чтобы какое-то действие можно было вызвать позже
public class CreateAccountCommand implements Command {
    private final BankAccountFacade facade;
    private final String id;
    private final String name;
    private final double balance;

    public CreateAccountCommand(BankAccountFacade f, String id, String name, double balance) {
        this.facade = f;
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    @Override
    public void execute() {
        facade.create(id, name, balance);
    }
}
