package org.example.facade;

import org.example.factory.DomainFactory;
import org.example.model.BankAccount;
import org.example.model.Category;
import org.example.repo.CategoryRepository;

import java.util.List;

public class CategoryFacade {
    private final CategoryRepository repo;
    private final DomainFactory factory;

    public CategoryFacade(CategoryRepository repo, DomainFactory factory) {
        this.repo = repo;
        this.factory = factory;
    }

    public void create(String id, Category.Type type, String name) {
        Category c = factory.createCategory(id, type, name);
        repo.save(c);
    }

    public void update(Category category) {
        repo.save(category);
    }

    public void delete(String id) {
        repo.delete(id);
    }

    public Category get(String id) {
        return repo.get(id);
    }

    public List<Category> getAll() {
        return repo.getAll();
    }
}
