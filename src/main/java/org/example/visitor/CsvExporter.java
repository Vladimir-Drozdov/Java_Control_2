package org.example.visitor;

import org.example.model.BankAccount;
import org.example.model.Category;
import org.example.model.Operation;

import java.io.FileWriter;
import java.io.IOException;
//Паттерн Посетитель (Visitor) — это поведенческий паттерн, цель которого — вынести операции над объектами в отдельный класс, не меняя сами объекты.
public class CsvExporter implements ExporterVisitor {

    private final String accountsFile = "accounts.csv";
    private final String categoriesFile = "categories.csv";
    private final String operationsFile = "operations.csv";

    @Override
    public void visit(BankAccount acc) {
        try (FileWriter writer = new FileWriter(accountsFile, true)) { // true = append
            writer.append(acc.getId()).append(",")
                    .append(acc.getName()).append(",")
                    .append(String.valueOf(acc.getBalance()))
                    .append("\n");
        } catch (IOException e) {
            System.err.println("Ошибка при экспорте счета: " + e.getMessage());
        }
    }

    @Override
    public void visit(Category cat) {
        try (FileWriter writer = new FileWriter(categoriesFile, true)) {
            writer.append(cat.getId()).append(",")
                    .append(cat.getType().toString()).append(",")
                    .append(cat.getName())
                    .append("\n");
        } catch (IOException e) {
            System.err.println("Ошибка при экспорте категории: " + e.getMessage());
        }
    }

    @Override
    public void visit(Operation op) {
        try (FileWriter writer = new FileWriter(operationsFile, true)) {
            writer.append(op.getId()).append(",")
                    .append(op.getType().toString()).append(",")
                    .append(op.getBankAccountId()).append(",")
                    .append(String.valueOf(op.getAmount())).append(",")
                    .append(op.getDate().toString()).append(",")
                    .append(op.getDescription()).append(",")
                    .append(op.getCategoryId())
                    .append("\n");
        } catch (IOException e) {
            System.err.println("Ошибка при экспорте операции: " + e.getMessage());
        }
    }
}
