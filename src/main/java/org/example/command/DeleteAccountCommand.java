package org.example.command;

import org.example.facade.BankAccountFacade;

public class DeleteAccountCommand implements Command {

    private final BankAccountFacade facade;
    private final String id;

    public DeleteAccountCommand(BankAccountFacade facade, String id) {
        this.facade = facade;
        this.id = id;
    }

    @Override
    public void execute() {
        if (facade.get(id) == null) {
            System.out.println("Счёт не найден: " + id);
            return;
        }
        facade.delete(id);
        System.out.println("Счёт удалён: " + id);
    }
}
