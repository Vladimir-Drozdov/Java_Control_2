package org.example.repo;

import org.example.model.Operation;
import java.util.List;

public interface OperationRepository {
    void save(Operation op);
    Operation get(String id);
    List<Operation> getAll();
    void delete(String id);
}
