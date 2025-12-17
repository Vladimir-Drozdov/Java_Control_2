package org.example.proxy;

import org.example.model.BankAccount;
import org.example.repo.BankAccountRepository;

import java.io.*;
import java.util.*;

public class BankAccountRepositoryProxy implements BankAccountRepository {

    private final BankAccountRepository realRepo;
    private final Map<String, BankAccount> cache = new HashMap<>();
    private final String filePath;

    public BankAccountRepositoryProxy(BankAccountRepository repo, String filePath) {
        this.realRepo = repo;
        this.filePath = filePath;

        try {
            List<BankAccount> accounts = loadFromFile(filePath);
            for (BankAccount acc : accounts) {
                realRepo.save(acc);
                cache.put(acc.getId(), acc);
            }
        } catch (IOException e) {
            System.out.println("Не удалось загрузить данные из файла: " + e.getMessage());
        }
    }

    @Override
    public void save(BankAccount acc) {
        cache.put(acc.getId(), acc);
        realRepo.save(acc);
        saveToFile();
    }

    @Override
    public BankAccount get(String id) {
        return cache.get(id);
    }

    @Override
    public void delete(String id) {
        cache.remove(id);
        realRepo.delete(id);
        saveToFile();
    }

    @Override
    public List<BankAccount> getAll() {
        return new ArrayList<>(cache.values());
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("id,name,balance");
            for (BankAccount acc : cache.values()) {
                writer.printf("%s,%s,%.2f%n", acc.getId(), acc.getName(), acc.getBalance());
            }
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении на диск: " + e.getMessage());
        }
    }

    private List<BankAccount> loadFromFile(String path) throws IOException {
        List<BankAccount> list = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            br.readLine(); // пропускаем заголовок
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    list.add(new BankAccount(parts[0], parts[1], Double.parseDouble(parts[2])));
                }
            }
        }
        return list;
    }
}
