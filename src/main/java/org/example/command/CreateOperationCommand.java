package org.example.command;

import org.example.facade.OperationFacade;
import org.example.model.Operation;

import java.time.LocalDate;

public class CreateOperationCommand implements Command {

    private final OperationFacade facade;
    private final String id;
    private final String type;
    private final String bankAccountId;
    private final double amount;
    private final LocalDate date;
    private final String description;
    private final String categoryId;

    public CreateOperationCommand(OperationFacade facade,
                                  String id, String type, String bankAccountId,
                                  double amount, LocalDate date, String description,
                                  String categoryId) {
        this.facade = facade;
        this.id = id;
        this.type = type;
        this.bankAccountId = bankAccountId;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.categoryId = categoryId;
    }

    @Override
    public void execute() {
        Operation.Type opType;
        try {
            opType = Operation.Type.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Неверный тип операции. Введите INCOME или EXPENSE");
            return;
        }

        Operation op = facade.create(
                id,
                opType,
                bankAccountId,
                amount,
                date,
                description,
                categoryId
        );

        System.out.println("Операция создана: " + op.getId() + ", " + op.getType() + ", " + op.getAmount());
    }
}
