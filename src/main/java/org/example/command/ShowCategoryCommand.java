package org.example.command;

import org.example.facade.CategoryFacade;
import org.example.model.Category;

public class ShowCategoryCommand implements Command {

    private final CategoryFacade facade;
    private final String id;

    public ShowCategoryCommand(CategoryFacade facade, String id) {
        this.facade = facade;
        this.id = id;
    }

    @Override
    public void execute() {
        Category cat = facade.get(id);
        if (cat == null) {
            System.out.println("Категория не найдена");
        } else {
            System.out.println("Категория: " + cat.getId() + ", " + cat.getType() + ", " + cat.getName());
        }
    }
}
