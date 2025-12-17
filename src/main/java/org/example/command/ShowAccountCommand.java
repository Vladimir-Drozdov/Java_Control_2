package org.example.command;

import org.example.facade.BankAccountFacade;
import org.example.model.BankAccount;

public class ShowAccountCommand implements Command {

    private final BankAccountFacade facade;
    private final String id;

    public ShowAccountCommand(BankAccountFacade facade, String id) {
        this.facade = facade;
        this.id = id;
    }

    @Override
    public void execute() {
        BankAccount acc = facade.get(id);
        if (acc == null) {
            System.out.println("Счёт не найден");
        } else {
            System.out.println("Счёт: " + acc.getId() + ", " + acc.getName() + ", " + acc.getBalance());
        }
    }
}
