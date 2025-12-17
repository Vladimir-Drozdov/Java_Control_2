package org.example.command;

import org.example.facade.OperationFacade;

public class DeleteOperationCommand implements Command {

    private final OperationFacade facade;
    private final String id;

    public DeleteOperationCommand(OperationFacade facade, String id) {
        this.facade = facade;
        this.id = id;
    }

    @Override
    public void execute() {
        if (facade.get(id) == null) {
            System.out.println("Операция не найдена: " + id);
            return;
        }
        facade.delete(id);
        System.out.println("Операция удалена: " + id);
    }
}
