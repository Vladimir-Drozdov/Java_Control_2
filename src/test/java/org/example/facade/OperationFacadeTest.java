package org.example.facade;

import org.example.factory.DomainFactory;
import org.example.model.Operation;
import org.example.repo.OperationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OperationFacadeTest {

    @Mock
    private OperationRepository repo;

    @Mock
    private DomainFactory factory;

    @InjectMocks
    private OperationFacade facade;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateFactoryAndSavesToRepo() {
        Operation op = new Operation("10", Operation.Type.INCOME, "acc1", 100,
                LocalDate.of(2025, 1, 1), "Test", "cat1");

        when(factory.createOperation(
                eq("10"),
                eq(Operation.Type.INCOME),
                eq("acc1"),
                eq(100.0),
                any(LocalDate.class),
                eq("Test"),
                eq("cat1")
        )).thenReturn(op);
        Operation result = facade.create("10", Operation.Type.INCOME, "acc1", 100,
                LocalDate.of(2025, 1, 1), "Test", "cat1");

        assertSame(op, result);
        verify(factory, times(1)).createOperation(
                "10", Operation.Type.INCOME, "acc1", 100, LocalDate.of(2025, 1, 1), "Test", "cat1"
        );
        verify(repo, times(1)).save(op);
    }

    @Test
    void testUpdate() {
        Operation op = new Operation("1", Operation.Type.INCOME, "A", 10, LocalDate.now(), "d", "c");
        facade.update(op);
        verify(repo, times(1)).save(op);
    }

    @Test
    void testDelete() {
        facade.delete("33");
        verify(repo, times(1)).delete("33");
    }

    @Test
    void testGet() {
        Operation op = new Operation("5", Operation.Type.EXPENSE, "A", 50, LocalDate.now(), "x", "y");
        when(repo.get("5")).thenReturn(op);

        Operation loaded = facade.get("5");
        assertSame(op, loaded);
        verify(repo, times(1)).get("5");
    }

    @Test
    void testGetAll() {
        Operation a = new Operation("1", Operation.Type.INCOME, "A", 10, LocalDate.now(), "d", "c");
        Operation b = new Operation("2", Operation.Type.EXPENSE, "B", 20, LocalDate.now(), "d2", "c2");
        when(repo.getAll()).thenReturn(List.of(a, b));

        List<Operation> list = facade.getAll();
        assertEquals(2, list.size());
        assertTrue(list.contains(a));
        assertTrue(list.contains(b));
        verify(repo, times(1)).getAll();
    }
}
