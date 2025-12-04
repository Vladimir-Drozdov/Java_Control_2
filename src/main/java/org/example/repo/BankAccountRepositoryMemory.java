// src/main/java/org/example/repository/BankAccountRepositoryMemory.java
package org.example.repo;

import org.example.model.BankAccount;
import java.util.*;
//Фасад - структурный паттерн, цель обеспечить простой интерфейс к сложной подсистеме, скрывая детали реализации
public class BankAccountRepositoryMemory implements BankAccountRepository {
    private final Map<String, BankAccount> accounts = new HashMap<>();

    @Override
    public void save(BankAccount account) {
        accounts.put(account.getId(), account);
    }

    @Override
    public BankAccount get(String id) {
        return accounts.get(id);
    }

    @Override
    public List<BankAccount> getAll() {
        return new ArrayList<>(accounts.values());
    }

    @Override
    public void delete(String id) {
        accounts.remove(id);
    }
}
