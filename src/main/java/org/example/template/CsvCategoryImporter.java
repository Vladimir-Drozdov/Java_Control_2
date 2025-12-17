package org.example.template;

import org.example.model.Category;
import java.util.ArrayList;
import java.util.List;

public class CsvCategoryImporter extends DataImporter<Category> {

    @Override
    protected List<Category> parse(String raw) {
        List<Category> list = new ArrayList<>();
        String[] lines = raw.split("\n");
        for (int i = 1; i < lines.length; i++) { // пропускаем заголовок
            String[] p = lines[i].split(",");
            list.add(new Category(p[0], Category.Type.valueOf(p[1].toUpperCase()), p[2]));
        }
        return list;
    }
}
