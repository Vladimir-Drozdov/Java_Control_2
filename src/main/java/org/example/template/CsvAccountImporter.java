package org.example.template;

import org.example.model.BankAccount;
import java.util.ArrayList;
import java.util.List;

public class CsvAccountImporter extends DataImporter<BankAccount> {

    @Override
    protected List<BankAccount> parse(String raw) {
        List<BankAccount> list = new ArrayList<>();
        String[] lines = raw.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String[] p = lines[i].split(",");
            list.add(new BankAccount(p[0], p[1], Double.parseDouble(p[2])));
        }
        return list;
    }
}
