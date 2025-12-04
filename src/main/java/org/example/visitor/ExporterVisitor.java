package org.example.visitor;

import org.example.model.BankAccount;
import org.example.model.Category;
import org.example.model.Operation;

public interface ExporterVisitor {
    void visit(BankAccount acc);
    void visit(Category cat);
    void visit(Operation op);
}