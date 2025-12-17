package org.example.command;

import org.example.facade.CategoryFacade;

public class DeleteCategoryCommand implements Command {

    private final CategoryFacade facade;
    private final String id;

    public DeleteCategoryCommand(CategoryFacade facade, String id) {
        this.facade = facade;
        this.id = id;
    }

    @Override
    public void execute() {
        if (facade.get(id) == null) {
            System.out.println("Категория не найдена: " + id);
            return;
        }
        facade.delete(id);
        System.out.println("Категория удалена: " + id);
    }
}
