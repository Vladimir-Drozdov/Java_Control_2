package org.example.command;

import org.example.facade.OperationFacade;
import org.example.model.Operation;

public class ShowOperationCommand implements Command {

    private final OperationFacade facade;
    private final String id;

    public ShowOperationCommand(OperationFacade facade, String id) {
        this.facade = facade;
        this.id = id;
    }

    @Override
    public void execute() {
        Operation op = facade.get(id);
        if (op == null) {
            System.out.println("Операция не найдена");
        } else {
            System.out.println("Операция: " + op.getId() + ", " + op.getType() + ", " + op.getAmount()
                    + ", счёт: " + op.getBankAccountId() + ", категория: " + op.getCategoryId()
                    + ", дата: " + op.getDate());
        }
    }
}
