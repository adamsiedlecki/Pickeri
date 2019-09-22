package pl.adamsiedlecki.Pickeri.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.adamsiedlecki.Pickeri.dao.ExpenseDAO;
import pl.adamsiedlecki.Pickeri.entity.Expense;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ExpenseService {

    private ExpenseDAO expenseDAO;

    @Autowired
    public ExpenseService(ExpenseDAO expenseDAO) {
        this.expenseDAO = expenseDAO;
    }

    public void save(Expense expense) {
        expenseDAO.save(expense);
    }

    public void delete(Expense expense) {
        expenseDAO.delete(expense);
    }

    public Expense getExpenseById(Long id) {
        return expenseDAO.getOne(id);
    }

    public List<Expense> findAll() {
        return expenseDAO.findAll();
    }

    public List<Expense> findAll(String filter) {
        List<Expense> expenses = expenseDAO.findAll();
        expenses.removeIf(expense -> !expense.getId().toString().contains(filter) && !expense.getName().contains(filter)
                && !expense.getTime().toString().contains(filter)
                && !expense.getMoneyAmount().toPlainString().contains(filter));
        return expenses;
    }

    public BigDecimal getTotalAmountOfSpentMoney() {
        List<Expense> expenses = findAll();
        BigDecimal totalMoney = new BigDecimal(0);
        for (Expense e : expenses) {
            totalMoney = totalMoney.add(e.getMoneyAmount());
        }
        return totalMoney;
    }

    public BigDecimal getAverageAmountOfSpentMoney() {
        List<Expense> expenses = findAll();
        if (expenses.size() == 0) {
            return new BigDecimal(0);
        }
        BigDecimal totalAmount = getTotalAmountOfSpentMoney();
        return totalAmount.divide(new BigDecimal(expenses.size()), 2, RoundingMode.FLOOR);
    }
}
