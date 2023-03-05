package com.store.service.imp;

import com.store.entity.order.Expense;
import org.springframework.stereotype.Service;

@Service
public interface ExpenseService {
    int addExpense(Expense expense);
}
