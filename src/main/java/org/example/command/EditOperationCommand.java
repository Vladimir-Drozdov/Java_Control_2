package org.example.command;

import org.example.facade.OperationFacade;
import org.example.model.Operation;

import java.time.LocalDate;

public class EditOperationCommand implements Command {

    private final OperationFacade facade;
    private final String id;
    private final String newTypeStr;         // ввод пользователя
    private final String newBankAccountId;
    private final double newAmount;
    private final LocalDate newDate;
    private final String newDescription;
    private final String newCategoryId;

    public EditOperationCommand(OperationFacade facade, String id, String newTypeStr, String newBankAccountId,
                                double newAmount, LocalDate newDate, String newDescription, String newCategoryId) {
        this.facade = facade;
        this.id = id;
        this.newTypeStr = newTypeStr;
        this.newBankAccountId = newBankAccountId;
        this.newAmount = newAmount;
        this.newDate = newDate;
        this.newDescription = newDescription;
        this.newCategoryId = newCategoryId;
    }

    @Override
    public void execute() {
        Operation op = facade.get(id);
        if (op == null) {
            System.out.println("Операция не найдена: " + id);
            return;
        }

        Operation.Type newType;
        try {
            newType = Operation.Type.valueOf(newTypeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Неверный тип операции. Введите INCOME или EXPENSE");
            return;
        }

        op.setType(newType);
        op.setBankAccountId(newBankAccountId);
        op.setAmount(newAmount);
        op.setDate(newDate);
        op.setDescription(newDescription);
        op.setCategoryId(newCategoryId);

        facade.update(op);
        System.out.println("Операция обновлена: " + id);
    }
}
