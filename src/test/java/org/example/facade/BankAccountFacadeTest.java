package org.example.facade;

import org.example.factory.DomainFactory;
import org.example.model.BankAccount;
import org.example.repo.BankAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BankAccountFacadeTest {

    @Mock
    private BankAccountRepository repo;

    @Mock
    private DomainFactory factory;

    @InjectMocks
    private BankAccountFacade facade;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateFactoryAndSavesToRepo() {
        BankAccount acc = new BankAccount("1", "TestAcc", 100.0);
        when(repo.get("1")).thenReturn(null);
        when(factory.createAccount("1", "TestAcc", 100.0)).thenReturn(acc);
        facade.create("1", "TestAcc", 100.0);
        verify(factory, times(1)).createAccount("1", "TestAcc", 100.0);
        verify(repo, times(1)).save(acc);
    }
    @Test
    void testCreateAccountAlreadyExistsThrows() {
        when(repo.get("1")).thenReturn(new BankAccount("1", "ExistAcc", 50));
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> facade.create("1", "TestAcc", 100.0));
        assertEquals("Account already exists", ex.getMessage());
        verify(factory, never()).createAccount(any(), any(), anyDouble());
        verify(repo, never()).save(any());
    }
    @Test
    void testUpdateAccountSavesToRepo() {
        BankAccount acc = new BankAccount("1", "Updated", 200.0);
        when(repo.get("1")).thenReturn(acc);
        facade.update(acc);
        verify(repo, times(1)).save(acc);
    }

    @Test
    void testUpdateAccountNotFoundThrows() {
        BankAccount acc = new BankAccount("1", "Updated", 200.0);
        when(repo.get("1")).thenReturn(null);
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> facade.update(acc));
        assertEquals("Account not found", ex.getMessage());
        verify(repo, never()).save(any());
    }
    @Test
    void testDelete() {
        facade.delete("1");
        verify(repo, times(1)).delete("1");
    }
    @Test
    void testGet() {
        BankAccount acc = new BankAccount("1", "TestAcc", 100.0);
        when(repo.get("1")).thenReturn(acc);
        BankAccount loaded = facade.get("1");
        assertSame(acc, loaded);
        verify(repo, times(1)).get("1");
    }

    @Test
    void testGetAll() {
        BankAccount a = new BankAccount("1", "A", 100.0);
        BankAccount b = new BankAccount("2", "B", 200.0);
        when(repo.getAll()).thenReturn(List.of(a, b));
        List<BankAccount> list = facade.getAll();
        assertEquals(2, list.size());
        assertTrue(list.contains(a));
        assertTrue(list.contains(b));
        verify(repo, times(1)).getAll();
    }
}
