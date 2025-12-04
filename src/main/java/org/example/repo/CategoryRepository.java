package org.example.repo;

import org.example.model.Category;
import java.util.*;

public class CategoryRepository {

    private final Map<String, Category> map = new HashMap<>();

    // Сохраняем категорию с ключом = id
    public void save(Category c) {
        map.put(c.getId(), c);
    }

    // Получаем категорию по id
    public Category get(String id) {
        return map.get(id);
    }

    // Получаем все категории
    public List<Category> getAll() {
        return new ArrayList<>(map.values());
    }

    // Удаляем категорию по id
    public void delete(String id) {
        map.remove(id);
    }
}
