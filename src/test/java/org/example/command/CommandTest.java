package org.example.command;

import org.example.facade.BankAccountFacade;
import org.example.facade.CategoryFacade;
import org.example.facade.OperationFacade;
import org.example.model.BankAccount;
import org.example.model.Category;
import org.example.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class CommandsTest {

    @Mock
    private BankAccountFacade bankFacade;
    @Mock
    private CategoryFacade categoryFacade;
    @Mock
    private OperationFacade operationFacade;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    //Account Commands
    @Test
    void testCreateAccountCommand() {
        CreateAccountCommand cmd = new CreateAccountCommand(bankFacade, "1", "TestAcc", 100);
        cmd.execute();
        verify(bankFacade, times(1)).create("1", "TestAcc", 100);
    }

    @Test
    void testEditAccountCommand() {
        when(bankFacade.get("1")).thenReturn(new BankAccount("1", "OldName", 100));
        EditAccountCommand cmd = new EditAccountCommand(bankFacade, "1", "NewName", 200);
        cmd.execute();
        verify(bankFacade, times(1)).update(any(BankAccount.class));
    }

    @Test
    void testDeleteAccountCommand() {
        when(bankFacade.get("1")).thenReturn(new BankAccount("1", "Test", 100));
        DeleteAccountCommand cmd = new DeleteAccountCommand(bankFacade, "1");
        cmd.execute();
        verify(bankFacade).delete("1");
    }

    @Test
    void testShowAccountCommand() {
        ShowAccountCommand cmd = new ShowAccountCommand(bankFacade, "1");
        cmd.execute();
        verify(bankFacade, times(1)).get("1");
    }
    // Category Commands
    @Test
    void testCreateCategoryCommand() {
        CreateCategoryCommand cmd = new CreateCategoryCommand(categoryFacade, "1", "INCOME", "Salary");
        // проверяем, что метод execute не кидает исключений и создаёт объект Category
        assertDoesNotThrow(cmd::execute);
    }

    @Test
    void testEditCategoryCommand() {
        when(categoryFacade.get("1")).thenReturn(new Category("1", Category.Type.INCOME, "Salary"));
        EditCategoryCommand cmd = new EditCategoryCommand(categoryFacade, "1", "EXPENSE", "Food");
        cmd.execute();
        verify(categoryFacade, times(1)).update(any(Category.class));
    }

    @Test
    void testDeleteCategoryCommand() {
        when(categoryFacade.get("1")).thenReturn(new Category("1", Category.Type.INCOME, "Salary"));
        DeleteCategoryCommand cmd = new DeleteCategoryCommand(categoryFacade, "1");
        cmd.execute();
        verify(categoryFacade, times(1)).delete("1");
    }

    @Test
    void testShowCategoryCommand() {
        ShowCategoryCommand cmd = new ShowCategoryCommand(categoryFacade, "1");
        cmd.execute();
        verify(categoryFacade, times(1)).get("1");
    }

    // Operation Commands
    @Test
    void testCreateOperationCommand() {
        CreateOperationCommand cmd = new CreateOperationCommand(
                operationFacade, "1", "INCOME", "acc1", 100,
                LocalDate.now(), "desc", "cat1"
        );
        Operation op = new Operation("1", Operation.Type.INCOME, "acc1", 100, LocalDate.now(), "desc", "cat1");
        when(operationFacade.create(any(), any(), any(), anyDouble(), any(), any(), any())).thenReturn(op);

        cmd.execute();
        verify(operationFacade, times(1))
                .create(eq("1"), eq(Operation.Type.INCOME), eq("acc1"), eq(100.0), any(), eq("desc"), eq("cat1"));
    }

    @Test
    void testEditOperationCommand() {
        when(operationFacade.get("1")).thenReturn(
                new Operation("1", Operation.Type.INCOME, "acc1", 100, LocalDate.now(), "desc", "cat1")
        );
        EditOperationCommand cmd = new EditOperationCommand(
                operationFacade, "1", "EXPENSE", "acc1", 50, LocalDate.now(), "cat1", "1"
        );
        cmd.execute();
        verify(operationFacade, times(1)).update(any(Operation.class));
    }


    @Test
    void testDeleteOperationCommand() {
        when(operationFacade.get("1")).thenReturn(new Operation(
                "1",
                Operation.Type.INCOME,
                "acc1",
                100,
                LocalDate.now(),
                "Test",
                "cat1"
        ));
        DeleteOperationCommand cmd = new DeleteOperationCommand(operationFacade, "1");
        cmd.execute();
        verify(operationFacade, times(1)).delete("1");
    }

    @Test
    void testShowOperationCommand() {
        ShowOperationCommand cmd = new ShowOperationCommand(operationFacade, "1");
        cmd.execute();
        verify(operationFacade, times(1)).get("1");
    }
}
