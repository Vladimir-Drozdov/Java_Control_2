package org.example.template;

import org.example.model.Operation;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CsvOperationImporter extends DataImporter<Operation> {

    @Override
    protected List<Operation> parse(String raw) {
        List<Operation> list = new ArrayList<>();
        String[] lines = raw.split("\n");
        for (int i = 1; i < lines.length; i++) {
            String[] p = lines[i].split(",");
            String id = p[0];
            String type = p[1];
            String bankId = p[2];
            double amount = Double.parseDouble(p[3]);
            LocalDate date = LocalDate.parse(p[4]);
            String description = p[5];
            String categoryId = p[6];
            list.add(new Operation(id, Operation.Type.valueOf(type.toUpperCase()), bankId, amount, date, description, categoryId));
        }
        return list;
    }
}
