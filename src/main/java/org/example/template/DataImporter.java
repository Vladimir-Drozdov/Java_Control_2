package org.example.template;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class DataImporter<T> {

    public final List<T> importData(String filename) {
        String raw = readFile(filename);
        return parse(raw);
    }

    protected String readFile(String filename) {
        try {
            return Files.readString(Path.of(filename));
        } catch (IOException e) {
            System.err.println("Не удалось прочитать файл: " + filename);
            throw new RuntimeException("Ошибка чтения CSV-файла: " + e.getMessage(), e);
        }
    }

    protected abstract List<T> parse(String raw);
}
