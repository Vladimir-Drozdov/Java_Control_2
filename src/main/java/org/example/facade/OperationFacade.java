package org.example.facade;

import org.example.factory.DomainFactory;
import org.example.model.Operation;
import org.example.repo.OperationRepository;

import java.time.LocalDate;
import java.util.List;

public class OperationFacade {

    private final OperationRepository repo;
    private final DomainFactory factory;

    public OperationFacade(OperationRepository repo, DomainFactory factory) {
        this.repo = repo;
        this.factory = factory;
    }

    // Метод create теперь принимает поля и использует фабрику
    public Operation create(String id, Operation.Type type, String bankAccountId,
                            double amount, LocalDate date, String description, String categoryId) {
        Operation op = factory.createOperation(id, type, bankAccountId, amount, date, description, categoryId);
        repo.save(op);
        return op;
    }

    public void update(Operation op) {
        repo.save(op);
    }

    public void delete(String id) {
        repo.delete(id);
    }

    public Operation get(String id) {
        return repo.get(id);
    }

    public List<Operation> getAll() {
        return repo.getAll();
    }
}
