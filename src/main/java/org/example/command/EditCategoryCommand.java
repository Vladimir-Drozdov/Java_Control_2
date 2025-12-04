package org.example.command;

import org.example.facade.CategoryFacade;
import org.example.model.Category;
import org.example.model.Operation;

public class EditCategoryCommand implements Command {

    private final CategoryFacade facade;
    private final String id;
    private final String newType;
    private final String newName;

    public EditCategoryCommand(CategoryFacade facade, String id, String newType, String newName) {
        this.facade = facade;
        this.id = id;
        this.newType = newType;
        this.newName = newName;
    }

    @Override
    public void execute() {
        Category cat = facade.get(id);
        if (cat == null) {
            System.out.println("Категория не найдена: " + id);
            return;
        }
        Category.Type newTypeEnum;
        try {
            newTypeEnum = Category.Type.valueOf(newType.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Неверный тип операции. Введите INCOME или EXPENSE");
            return;
        }
        cat.setType(newTypeEnum);
        cat.setName(newName);
        facade.update(cat);
        System.out.println("Категория обновлена: " + id + ", " + newType + ", " + newName);
    }
}
