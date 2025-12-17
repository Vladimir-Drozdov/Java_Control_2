package org.example.repo;

import org.example.model.Operation;
import java.util.*;

public class OperationRepositoryMemory implements OperationRepository {
    private final Map<String, Operation> operations = new HashMap<>();

    @Override
    public void save(Operation op) {
        operations.put(op.getId(), op);
    }

    @Override
    public Operation get(String id) {
        return operations.get(id);
    }

    @Override
    public List<Operation> getAll() {
        return new ArrayList<>(operations.values());
    }

    @Override
    public void delete(String id) {
        operations.remove(id);
    }
}
