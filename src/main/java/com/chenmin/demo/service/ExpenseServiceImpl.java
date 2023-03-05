package com.store.service;

import com.store.entity.order.Expense;
import com.store.mapper.ExpenseMapper;
import com.store.service.imp.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("expense")
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    ExpenseMapper expenseMapper;

    @Override
    public int addExpense(Expense expense) {
        return expenseMapper.addExpense(expense);
    }
}
