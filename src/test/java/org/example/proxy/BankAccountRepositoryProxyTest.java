package org.example.proxy;

import org.example.model.BankAccount;
import org.example.repo.BankAccountRepository;
import org.example.repo.BankAccountRepositoryMemory;
import org.junit.jupiter.api.*;
import java.io.File;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountRepositoryProxyTest {

    private File tempFile;
    private BankAccountRepository realRepo;
    private BankAccountRepositoryProxy proxy;

    @BeforeEach
    void setup() throws Exception {
        tempFile = File.createTempFile("accounts", ".csv");
        tempFile.deleteOnExit();
        realRepo = new BankAccountRepositoryMemory();
        proxy = new BankAccountRepositoryProxy(realRepo, tempFile.getAbsolutePath());
    }

    @Test
    void testSaveAndGet() {
        BankAccount acc = new BankAccount("1", "Test", 100.0);
        proxy.save(acc);

        BankAccount loaded = proxy.get("1");
        assertNotNull(loaded);
        assertEquals("Test", loaded.getName());
        assertEquals(100.0, loaded.getBalance());
    }

    @Test
    void testDelete() {
        BankAccount acc = new BankAccount("1", "Test", 100.0);
        proxy.save(acc);

        proxy.delete("1");
        assertNull(proxy.get("1"));
    }

    @Test
    void testGetAll() {
        proxy.save(new BankAccount("1", "A", 1));
        proxy.save(new BankAccount("2", "B", 2));

        List<BankAccount> all = proxy.getAll();
        assertEquals(2, all.size());
    }

    @Test
    void testPersistenceBetweenRuns() {
        proxy.save(new BankAccount("1", "Persist", 777.0));

        BankAccountRepositoryProxy proxy2 =
                new BankAccountRepositoryProxy(new BankAccountRepositoryMemory(), tempFile.getAbsolutePath());

        BankAccount loaded = proxy2.get("1");
        assertNotNull(loaded);
        assertEquals(777.0, loaded.getBalance());
    }
}
