package org.example.console;

import org.example.command.*;
import org.example.command.decorators.TimingDecorator;
import org.example.facade.BankAccountFacade;
import org.example.facade.CategoryFacade;
import org.example.facade.OperationFacade;
import org.example.model.BankAccount;
import org.example.model.Category;
import org.example.model.Operation;
import org.example.template.CsvAccountImporter;
import org.example.template.CsvCategoryImporter;
import org.example.template.CsvOperationImporter;
import org.example.visitor.ExporterVisitor;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AppConsole {

    private final BankAccountFacade bankFacade;
    private final CategoryFacade categoryFacade;
    private final OperationFacade operationFacade;
    private final ExporterVisitor csvExporter;

    public AppConsole(
            BankAccountFacade bankFacade,
            CategoryFacade categoryFacade,
            OperationFacade operationFacade,
            ExporterVisitor csvExporter
    ) {
        this.bankFacade = bankFacade;
        this.categoryFacade = categoryFacade;
        this.operationFacade = operationFacade;
        this.csvExporter = csvExporter;
    }

    public void run() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Создать счёт");
            System.out.println("2. Показать счёт");
            System.out.println("3. Редактировать счёт");
            System.out.println("4. Удалить счёт");
            System.out.println("5. Создать категорию");
            System.out.println("6. Показать категорию");
            System.out.println("7. Редактировать категорию");
            System.out.println("8. Удалить категорию");
            System.out.println("9. Создать операцию");
            System.out.println("10. Показать операцию");
            System.out.println("11. Редактировать операцию");
            System.out.println("12. Удалить операцию");
            System.out.println("13. Экспорт всех счетов в CSV");
            System.out.println("14. Экспорт всех категорий и операций в CSV");
            System.out.println("15. Импорт счетов из CSV");
            System.out.println("16. Импорт категорий из CSV");
            System.out.println("17. Импорт операций из CSV");
            System.out.println("0. Выход");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.println("Введите id, name, balance:");
                    String id = sc.next();
                    String name = sc.next();
                    double balance = sc.nextDouble();
                    execute(new CreateAccountCommand(bankFacade, id, name, balance));
                }
                case 2 -> {
                    System.out.println("Введите id счёта:");
                    execute(new ShowAccountCommand(bankFacade, sc.next()));
                }
                case 3 -> {
                    System.out.println("Введите id, новое имя, новый баланс:");
                    execute(new EditAccountCommand(
                            bankFacade, sc.next(), sc.next(), sc.nextDouble()
                    ));
                }
                case 4 -> {
                    System.out.println("Введите id счёта:");
                    execute(new DeleteAccountCommand(bankFacade, sc.next()));
                }
                case 5 -> {
                    System.out.println("Введите id, type, name:");
                    execute(new CreateCategoryCommand(
                            categoryFacade, sc.next(), sc.next(), sc.next()
                    ));
                }
                case 6 -> {
                    System.out.println("Введите id категории:");
                    execute(new ShowCategoryCommand(categoryFacade, sc.next()));
                }
                case 7 -> {
                    System.out.println("Введите id, новый type, новое name:");
                    execute(new EditCategoryCommand(
                            categoryFacade, sc.next(), sc.next(), sc.next()
                    ));
                }
                case 8 -> {
                    System.out.println("Введите id категории:");
                    execute(new DeleteCategoryCommand(categoryFacade, sc.next()));
                }
                case 9 -> {
                    System.out.println("Введите id, type, bankId, amount, categoryId:");
                    execute(new CreateOperationCommand(
                            operationFacade,
                            sc.next(),
                            sc.next(),
                            sc.next(),
                            sc.nextDouble(),
                            LocalDate.now(),
                            "",
                            sc.next()
                    ));
                }
                case 10 -> {
                    System.out.println("Введите id операции:");
                    execute(new ShowOperationCommand(operationFacade, sc.next()));
                }
                case 11 -> {
                    System.out.println("Введите id, type, bankId, amount, categoryId:");
                    execute(new EditOperationCommand(
                            operationFacade,
                            sc.next(),
                            sc.next(),
                            sc.next(),
                            sc.nextDouble(),
                            LocalDate.now(),
                            "",
                            sc.next()
                    ));
                }
                case 12 -> {
                    System.out.println("Введите id операции:");
                    execute(new DeleteOperationCommand(operationFacade, sc.next()));
                }
                case 13 -> {
                    List<BankAccount> accounts = bankFacade.getAll();
                    accounts.forEach(acc -> acc.accept(csvExporter));
                }
                case 14 -> {
                    categoryFacade.getAll().forEach(c -> c.accept(csvExporter));
                    operationFacade.getAll().forEach(o -> o.accept(csvExporter));
                }
                case 15 -> importAccounts(sc);
                case 16 -> importCategories(sc);
                case 17 -> importOperations(sc);
                case 0 -> {
                    System.out.println("Выход.");
                    return;
                }
                default -> System.out.println("Неверный ввод.");
            }
        }
    }

    private void execute(Command cmd) {
        new TimingDecorator(cmd).execute();
    }

    private void importAccounts(Scanner sc) {
        System.out.println("Путь к CSV:");
        try {
            for (var acc : new CsvAccountImporter().importData(sc.nextLine())) {
                bankFacade.create(acc.getId(), acc.getName(), acc.getBalance());
            }
        } catch (Exception e) {
            System.out.println("Ошибка импорта счетов: " + e.getMessage());
        }
    }

    private void importCategories(Scanner sc) {
        System.out.println("Путь к CSV:");
        try {
            for (var cat : new CsvCategoryImporter().importData(sc.nextLine())) {
                categoryFacade.create(cat.getId(), cat.getType(), cat.getName());
            }
        } catch (Exception e) {
            System.out.println("Ошибка импорта категорий: " + e.getMessage());
        }
    }

    private void importOperations(Scanner sc) {
        System.out.println("Путь к CSV:");
        try {
            for (var op : new CsvOperationImporter().importData(sc.nextLine())) {
                operationFacade.create(
                        op.getId(),
                        op.getType(),
                        op.getBankAccountId(),
                        op.getAmount(),
                        op.getDate(),
                        op.getDescription(),
                        op.getCategoryId()
                );
            }
        } catch (Exception e) {
            System.out.println("Ошибка импорта операций: " + e.getMessage());
        }
    }
}
