package org.example.importer;

import org.example.model.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.example.template.CsvAccountImporter;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CsvAccountImporterTest {

    private File tempFile;

    @BeforeEach
    void setup() throws Exception {
        tempFile = File.createTempFile("acc", ".csv");
        tempFile.deleteOnExit();
    }

    private void write(String text) throws Exception {
        try (FileWriter fw = new FileWriter(tempFile)) {
            fw.write(text);
        }
    }

    @Test
    void testImportSingleRow() throws Exception {
        // id,name,balance
        write("1,Main,1500");

        CsvAccountImporter importer = new CsvAccountImporter();
        List<BankAccount> list = importer.importData(tempFile.getAbsolutePath());

        assertEquals(1, list.size());
        BankAccount acc = list.get(0);
        assertEquals("1", acc.getId());
        assertEquals("Main", acc.getName());
        assertEquals(1500, acc.getBalance());
    }

    @Test
    void testMultipleRows() throws Exception {
        // id,name,balance
        write("1,Main,100\n" +
                "2,Savings,200\n" +
                "3,Card,300");

        CsvAccountImporter importer = new CsvAccountImporter();
        List<BankAccount> list = importer.importData(tempFile.getAbsolutePath());

        assertEquals(3, list.size());
        assertEquals("2", list.get(1).getId());
        assertEquals("Savings", list.get(1).getName());
        assertEquals(200, list.get(1).getBalance());
    }

    @Test
    void testSpacesTrimmed() throws Exception {
        // id,name,balance
        write("99,Cash,777");

        CsvAccountImporter importer = new CsvAccountImporter();
        List<BankAccount> list = importer.importData(tempFile.getAbsolutePath());

        assertEquals(1, list.size());
        BankAccount acc = list.get(0);
        assertEquals("99", acc.getId());
        assertEquals("Cash", acc.getName());
        assertEquals(777, acc.getBalance());
    }

    @Test
    void testEmptyFile() throws Exception {
        write("");
        CsvAccountImporter importer = new CsvAccountImporter();
        List<BankAccount> list = importer.importData(tempFile.getAbsolutePath());
        assertEquals(0, list.size());
    }
}
