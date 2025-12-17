package org.example.importer;

import org.example.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.example.template.CsvCategoryImporter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CsvCategoryImporterTest {

    private File tempFile;

    @BeforeEach
    void setup() throws Exception {
        tempFile = File.createTempFile("cat", ".csv");
        tempFile.deleteOnExit();
    }

    private void write(String text) throws Exception {
        try (FileWriter fw = new FileWriter(tempFile)) {
            fw.write(text);
        }
    }

    @Test
    void testImportSingleRow() throws Exception {
        write("id,type,name\n1,INCOME,Salary");

        CsvCategoryImporter importer = new CsvCategoryImporter();
        List<Category> list = importer.importData(tempFile.getAbsolutePath());

        assertEquals(1, list.size());
        Category cat = list.get(0);
        assertEquals("1", cat.getId());
        assertEquals(Category.Type.INCOME, cat.getType());
        assertEquals("Salary", cat.getName());
    }

    @Test
    void testEmptyFile() throws Exception {
        write("");
        CsvCategoryImporter importer = new CsvCategoryImporter();
        List<Category> list = importer.importData(tempFile.getAbsolutePath());
        assertEquals(0, list.size());
    }

    @Test
    void testOnlyHeader() throws Exception {
        write("id,type,name\n");
        CsvCategoryImporter importer = new CsvCategoryImporter();
        List<Category> list = importer.importData(tempFile.getAbsolutePath());
        assertEquals(0, list.size());
    }

    @Test
    void testMultipleRows() throws Exception {
        write("""
                id,type,name
                1,INCOME,Salary
                2,EXPENSE,Food
                3,EXPENSE,Games
                """);

        CsvCategoryImporter importer = new CsvCategoryImporter();
        List<Category> list = importer.importData(tempFile.getAbsolutePath());
        assertEquals(3, list.size());
        assertEquals("2", list.get(1).getId());
        assertEquals(Category.Type.EXPENSE, list.get(1).getType());
        assertEquals("Food", list.get(1).getName());
    }

    @Test
    void testSpacesTrimmed() throws Exception {
        write("id,type,name\n5,EXPENSE,Coffee");

        CsvCategoryImporter importer = new CsvCategoryImporter();
        List<Category> list = importer.importData(tempFile.getAbsolutePath());
        assertEquals(1, list.size());
        Category cat = list.get(0);
        assertEquals("5", cat.getId());
        assertEquals(Category.Type.EXPENSE, cat.getType());
        assertEquals("Coffee", cat.getName());
    }

    @Test
    void testInvalidType() throws Exception {
        write("""
                id,type,name
                1,WRONGTYPE,Stuff
                """);

        CsvCategoryImporter importer = new CsvCategoryImporter();
        assertThrows(IllegalArgumentException.class,
                () -> importer.importData(tempFile.getAbsolutePath()));
    }

    @Test
    void testInvalidLine() throws Exception {
        write("""
                id,type,name
                1,INCOME,Salary
                invalid_line
                """);

        CsvCategoryImporter importer = new CsvCategoryImporter();
        assertThrows(Exception.class,
                () -> importer.importData(tempFile.getAbsolutePath()));
    }
}
