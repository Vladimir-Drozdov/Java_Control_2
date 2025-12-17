package org.example.command;

import org.example.facade.CategoryFacade;
import org.example.model.Category;

public class CreateCategoryCommand implements Command {

    private final CategoryFacade facade;
    private final String id;
    private final String type;
    private final String name;

    public CreateCategoryCommand(CategoryFacade facade, String id, String type, String name) {
        this.facade = facade;
        this.id = id;
        this.type = type;
        this.name = name;
    }

    @Override
    public void execute() {
        Category cat = new Category(id, Category.Type.valueOf(type.toUpperCase()), name);
        System.out.println("Категория создана: " + cat.getId() + ", " + cat.getType() + ", " + cat.getName());
    }

}
