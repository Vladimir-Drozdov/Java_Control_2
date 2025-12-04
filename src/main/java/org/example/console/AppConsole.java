package org.example.console;

import org.example.command.*;
import org.example.command.decorators.TimingDecorator;
import org.example.facade.BankAccountFacade;
import org.example.facade.CategoryFacade;
import org.example.facade.OperationFacade;
import org.example.factory.DomainFactory;
import org.example.model.BankAccount;
import org.example.model.Category;
import org.example.model.Operation;
import org.example.proxy.BankAccountRepositoryProxy;
import org.example.repo.BankAccountRepositoryMemory;
import org.example.repo.BankAccountRepository;
import org.example.repo.CategoryRepository;
import org.example.repo.OperationRepositoryMemory;
import org.example.repo.OperationRepository;
import org.example.template.CsvAccountImporter;
import org.example.template.CsvCategoryImporter;
import org.example.template.CsvOperationImporter;
import org.example.visitor.CsvExporter;
import org.example.visitor.ExporterVisitor;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;


public class AppConsole {

    public void run() {
        Scanner sc = new Scanner(System.in);
        DomainFactory factory = new DomainFactory();

        BankAccountRepository realRepo = new BankAccountRepositoryMemory();
        BankAccountRepository proxyRepo = new BankAccountRepositoryProxy(realRepo, "accounts.csv");
        BankAccountFacade bankFacade = new BankAccountFacade(proxyRepo, factory);

        CategoryFacade categoryFacade = new CategoryFacade(new CategoryRepository(), factory);
        OperationFacade operationFacade = new OperationFacade(new OperationRepositoryMemory(), factory);
        ExporterVisitor csvExporter = new CsvExporter();

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
                    Command cmd = new CreateAccountCommand(bankFacade, id, name, balance);
                    new TimingDecorator(cmd).execute();
                }
                case 2 -> {
                    System.out.println("Введите id счёта:");
                    String id = sc.next();
                    Command cmd = new ShowAccountCommand(bankFacade, id);
                    new TimingDecorator(cmd).execute();
                }
                case 3 -> {
                    System.out.println("Введите id счёта, новое имя, новый баланс:");
                    String id = sc.next();
                    String newName = sc.next();
                    double newBalance = sc.nextDouble();
                    Command cmd = new EditAccountCommand(bankFacade, id, newName, newBalance);
                    new TimingDecorator(cmd).execute();
                }
                case 4 -> {
                    System.out.println("Введите id счёта для удаления:");
                    String id = sc.next();
                    Command cmd = new DeleteAccountCommand(bankFacade, id);
                    new TimingDecorator(cmd).execute();
                }
                case 5 -> {
                    System.out.println("Введите id, type(income/expense), name категории:");
                    String id = sc.next();
                    String typeInput = sc.next();
                    String name = sc.next();
                    Command cmd = new CreateCategoryCommand(categoryFacade, id, typeInput, name);
                    new TimingDecorator(cmd).execute();
                }
                case 6 -> {
                    System.out.println("Введите id категории:");
                    String id = sc.next();
                    Command cmd = new ShowCategoryCommand(categoryFacade, id);
                    new TimingDecorator(cmd).execute();
                }
                case 7 -> {
                    System.out.println("Введите id категории, новый type, новое name:");
                    String id = sc.next();
                    String newType = sc.next();
                    String newName = sc.next();
                    Command cmd = new EditCategoryCommand(categoryFacade, id, newType, newName);
                    new TimingDecorator(cmd).execute();
                }
                case 8 -> {
                    System.out.println("Введите id категории для удаления:");
                    String id = sc.next();
                    Command cmd = new DeleteCategoryCommand(categoryFacade, id);
                    new TimingDecorator(cmd).execute();
                }
                case 9 -> {
                    System.out.println("Введите id, type(income/expense), bankAccountId, amount, categoryId:");
                    String id = sc.next();
                    String type = sc.next();
                    String bankId = sc.next();
                    double amount = sc.nextDouble();
                    String categoryId = sc.next();
                    LocalDate date = LocalDate.now();
                    Command cmd = new CreateOperationCommand(operationFacade, id, type, bankId, amount, date, "", categoryId);
                    new TimingDecorator(cmd).execute();
                }
                case 10 -> {
                    System.out.println("Введите id операции:");
                    String id = sc.next();
                    Command cmd = new ShowOperationCommand(operationFacade, id);
                    new TimingDecorator(cmd).execute();
                }
                case 11 -> {
                    System.out.println("Введите id операции, новый type, новый bankAccountId, новый amount, новый categoryId:");
                    String id = sc.next();
                    String type = sc.next();
                    String bankId = sc.next();
                    double amount = sc.nextDouble();
                    String categoryId = sc.next();
                    LocalDate date = LocalDate.now();
                    Command cmd = new EditOperationCommand(operationFacade, id, type, bankId, amount, date, "", categoryId);
                    new TimingDecorator(cmd).execute();
                }
                case 12 -> {
                    System.out.println("Введите id операции для удаления:");
                    String id = sc.next();
                    Command cmd = new DeleteOperationCommand(operationFacade, id);
                    new TimingDecorator(cmd).execute();
                }
                case 13 -> {
                    System.out.println("Экспорт счетов в CSV:");
                    List<BankAccount> accounts = bankFacade.getAll();
                    for (BankAccount acc : accounts) {
                        acc.accept(csvExporter);
                    }
                }

                case 14 -> {
                    System.out.println("Экспорт категорий и операций в CSV:");

                    List<Category> categories = categoryFacade.getAll();
                    for (Category cat : categories) {
                        cat.accept(csvExporter);
                    }

                    List<Operation> operations = operationFacade.getAll();
                    for (Operation op : operations) {
                        op.accept(csvExporter);
                    }
                }
                case 15 -> {
                    System.out.println("Введите путь к CSV-файлу со счетами:");
                    String path = sc.nextLine();

                    CsvAccountImporter importer = new CsvAccountImporter();
                    try {
                        for (var acc : importer.importData(path)) {
                            bankFacade.create(
                                    acc.getId(),
                                    acc.getName(),
                                    acc.getBalance()
                            );
                        }
                        System.out.println("Счета импортированы.");
                    } catch (Exception e) {
                        System.out.println("Ошибка при импорте счетов: " + e.getMessage());
                    }
                }

                case 16 -> {
                    System.out.println("Введите путь к CSV-файлу с категориями:");
                    String path = sc.nextLine();

                    CsvCategoryImporter importer = new CsvCategoryImporter();
                    try {
                        for (var cat : importer.importData(path)) {
                            categoryFacade.create(
                                    cat.getId(),
                                    cat.getType(),
                                    cat.getName()
                            );
                        }
                        System.out.println("Категории импортированы.");
                    } catch (Exception e) {
                        System.out.println("Ошибка при импорте категорий: " + e.getMessage());
                    }
                }

                case 17 -> {
                    System.out.println("Введите путь к CSV-файлу с операциями:");
                    String path = sc.nextLine();

                    CsvOperationImporter importer = new CsvOperationImporter();
                    try {
                        for (var rawOp : importer.importData(path)) {
                            Operation.Type opType;
                            try {
                                opType = Operation.Type.valueOf(rawOp.getType().toString().toUpperCase());
                            } catch (IllegalArgumentException e) {
                                System.out.println("Неверный тип операции для id " + rawOp.getId() + ". Пропущена.");
                                continue;
                            }

                            operationFacade.create(
                                    rawOp.getId(),
                                    opType,
                                    rawOp.getBankAccountId(),
                                    rawOp.getAmount(),
                                    rawOp.getDate(),
                                    rawOp.getDescription(),
                                    rawOp.getCategoryId()
                            );
                        }
                        System.out.println("Операции импортированы.");
                    } catch (Exception e) {
                        System.out.println("Ошибка при импорте операций: " + e.getMessage());
                    }
                }
                case 0 -> {
                    System.out.println("Выход из приложения.");
                    return;
                }
                default -> System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }
}
