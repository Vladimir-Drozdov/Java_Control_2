package org.example.facade;

import org.example.factory.DomainFactory;
import org.example.model.BankAccount;
import org.example.repo.BankAccountRepository;
import java.util.List;

public class BankAccountFacade {

    private final BankAccountRepository repo;
    private final DomainFactory factory;

    public BankAccountFacade(BankAccountRepository repo, DomainFactory factory) {
        this.repo = repo;
        this.factory = factory;
    }

    public void create(String id, String name, double balance) {
        if (repo.get(id) != null) throw new IllegalArgumentException("Account already exists");
        BankAccount acc = factory.createAccount(id, name, balance);
        repo.save(acc);
    }

    public void update(BankAccount acc) {
        if (repo.get(acc.getId()) == null) throw new IllegalArgumentException("Account not found");
        repo.save(acc);
    }

    public void delete(String id) {
        repo.delete(id);
    }

    public BankAccount get(String id) {
        return repo.get(id);
    }

    public List<BankAccount> getAll() {
        return repo.getAll();
    }
}
