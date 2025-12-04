package org.example.model;

import org.example.visitor.ExporterVisitor;

public class Category {

    public enum Type {
        INCOME, EXPENSE
    }

    private final String id;      // уникальный идентификатор
    private Type type;            // "INCOME" или "EXPENSE"
    private String name;          // название категории

    public Category(String id, Type type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void accept(ExporterVisitor v) {
        v.visit(this);
    }
}
