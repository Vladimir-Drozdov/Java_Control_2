// src/main/java/org/example/repository/BankAccountRepository.java
package org.example.repo;

import org.example.model.BankAccount;
import java.util.List;

public interface BankAccountRepository {
    void save(BankAccount account);        // добавить или обновить
    BankAccount get(String id);            // получить по id
    List<BankAccount> getAll();            // получить все
    void delete(String id);                // удалить по id
}
