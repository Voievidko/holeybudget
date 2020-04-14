package com.notspend.service.impl.sync;

import com.notspend.entity.Account;
import com.notspend.entity.Category;
import com.notspend.entity.User;
import com.notspend.service.CategoryService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MonobankSyncServiceImplTest {

    private List<Account> accounts = new ArrayList<>();

    List<Category> categories = new ArrayList<>();

    @Mock
    private CategoryService categoryService;

    @Mock
    private MonobankSyncServiceImpl.RunnableMono runnableMonoMock;

    @BeforeAll
    void prepareData(){
        User user = new User();

        Account account1 = new Account();
        account1.setUser(user);
        account1.setSynchronizationId("id1");
        account1.setToken("testtoken1");

        Account account2 = new Account();
        account2.setUser(user);
        account2.setSynchronizationId("id1");
        account2.setToken("testtoken1");

        accounts.add(account1);
        accounts.add(account2);

        Category category1 = new Category();
        category1.setCategoryId(1);
        category1.setDescription("");
        category1.setIncome(false);
        category1.setName("TestCategory");
        category1.setUser(new User());
        categories.add(category1);
    }

    @Test
    void syncDataWithBankServer() throws Exception {

//        when(categoryService.getAllExpenseCategories()).thenReturn(categories);
        when(runnableMonoMock.getJsonWithStatements(anyLong(), anyLong())).thenReturn(Optional.of("{}"));
        verify(runnableMonoMock, times(1)).getJsonWithStatements(anyLong(), anyLong());

    }
}