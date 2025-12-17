package org.example.importer;

import org.example.model.Operation;
import org.example.template.CsvOperationImporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CsvOperationImporterTest {

    private File tempFile;

    @BeforeEach
    void setup() throws Exception {
        tempFile = File.createTempFile("operations", ".csv");
        tempFile.deleteOnExit();
    }

    private void write(String content) throws Exception {
        try (FileWriter fw = new FileWriter(tempFile)) {
            fw.write(content);
        }
    }

    @Test
    void testImportSingleRow() throws Exception {
        // id,type,bankId,amount,date,description,categoryId
        write("id,type,bankId,amount,date,description,categoryId\n" +
                "1,INCOME,acc1,100,2025-01-01,Test,cat1");

        CsvOperationImporter importer = new CsvOperationImporter();
        List<Operation> list = importer.importData(tempFile.getAbsolutePath());

        assertEquals(1, list.size());
        Operation op = list.get(0);
        assertEquals("1", op.getId());
        assertEquals(Operation.Type.INCOME, op.getType());
        assertEquals("acc1", op.getBankAccountId());
        assertEquals(100, op.getAmount());
        assertEquals("Test", op.getDescription());
        assertEquals("cat1", op.getCategoryId());
        assertEquals(LocalDate.of(2025, 1, 1), op.getDate());
    }

    @Test
    void testEmptyFile() throws Exception {
        write("id,type,bankId,amount,date,description,categoryId\n"); // только заголовок

        CsvOperationImporter importer = new CsvOperationImporter();
        List<Operation> list = importer.importData(tempFile.getAbsolutePath());

        assertEquals(0, list.size());
    }

    @Test
    void testInvalidNumber() throws Exception {
        write("id,type,bankId,amount,date,description,categoryId\n" +
                "1,INCOME,acc1,abc,2025-01-01,Test,cat1");

        CsvOperationImporter importer = new CsvOperationImporter();
        assertThrows(NumberFormatException.class,
                () -> importer.importData(tempFile.getAbsolutePath()));
    }

    @Test
    void testInvalidCsvLine() throws Exception {
        write("id,type,bankId,amount,date,description,categoryId\n" +
                "1,INCOME,acc1,100,2025-01-01,Test,cat1\n" +
                "invalid_line\n" +
                "2,EXPENSE,acc2,200,2025-01-02,Buy,cat2");

        CsvOperationImporter importer = new CsvOperationImporter();
        assertThrows(Exception.class,
                () -> importer.importData(tempFile.getAbsolutePath()));
    }
}
