package org.example.facade;

import org.example.factory.DomainFactory;
import org.example.model.Category;
import org.example.repo.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryFacadeTest {

    @Mock
    private CategoryRepository repo;

    @Mock
    private DomainFactory factory;

    @InjectMocks
    private CategoryFacade facade;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateFactoryAndSavesToRepo() {
        Category cat = new Category("1", Category.Type.INCOME, "Salary");
        when(factory.createCategory("1", Category.Type.INCOME, "Salary")).thenReturn(cat);
        facade.create("1", Category.Type.INCOME, "Salary");
        verify(factory, times(1)).createCategory("1", Category.Type.INCOME, "Salary");
        verify(repo, times(1)).save(cat);
    }

    @Test
    void testUpdate() {
        Category cat = new Category("2", Category.Type.EXPENSE, "Food");
        facade.update(cat);
        verify(repo, times(1)).save(cat);
    }

    @Test
    void testDelete() {
        facade.delete("3");
        verify(repo, times(1)).delete("3");
    }

    @Test
    void testGet() {
        Category cat = new Category("4", Category.Type.INCOME, "Bonus");
        when(repo.get("4")).thenReturn(cat);
        Category loaded = facade.get("4");
        assertSame(cat, loaded);
        verify(repo, times(1)).get("4");
    }

    @Test
    void testGetAll() {
        Category a = new Category("1", Category.Type.INCOME, "Salary");
        Category b = new Category("2", Category.Type.EXPENSE, "Rent");
        when(repo.getAll()).thenReturn(List.of(a, b));

        List<Category> list = facade.getAll();
        assertEquals(2, list.size());
        assertTrue(list.contains(a));
        assertTrue(list.contains(b));
        verify(repo, times(1)).getAll();
    }
}
