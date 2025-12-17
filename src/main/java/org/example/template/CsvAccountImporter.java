package org.example.template;

import org.example.model.BankAccount;
import java.util.ArrayList;
import java.util.List;

public class CsvAccountImporter extends DataImporter<BankAccount> {

    @Override
    protected List<BankAccount> parse(String raw) {
        List<BankAccount> list = new ArrayList<>();
        String[] lines = raw.split("\n");
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue; // пропускаем пустые строки
            String[] parts = line.split(",");
            if (parts.length < 3) continue; // пропускаем некорректные строки
            String id = parts[0].trim();
            String name = parts[1].trim();
            double balance = Double.parseDouble(parts[2].trim());
            list.add(new BankAccount(id, name, balance));
        }
        return list;
    }
}
