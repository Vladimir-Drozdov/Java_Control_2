package org.example.command;

import org.example.facade.BankAccountFacade;
import org.example.model.BankAccount;

public class EditAccountCommand implements Command {

    private final BankAccountFacade facade;
    private final String id;
    private final String newName;
    private final double newBalance;

    public EditAccountCommand(BankAccountFacade facade, String id, String newName, double newBalance) {
        this.facade = facade;
        this.id = id;
        this.newName = newName;
        this.newBalance = newBalance;
    }

    @Override
    public void execute() {
        BankAccount acc = facade.get(id);
        if (acc == null) {
            System.out.println("Счёт не найден: " + id);
            return;
        }
        acc.setName(newName);
        acc.setBalance(newBalance);
        facade.update(acc);
        System.out.println("Счёт обновлён: " + id + ", " + newName + ", " + newBalance);
    }
}
