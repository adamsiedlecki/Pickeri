package pl.adamsiedlecki.Pickeri.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.adamsiedlecki.Pickeri.dao.ExpenseDAO;
import pl.adamsiedlecki.Pickeri.entity.Expense;
import pl.adamsiedlecki.Pickeri.interfaces.Removeable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ExpenseService implements Removeable<Expense> {

    private final ExpenseDAO expenseDAO;

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
        return expenseDAO.findAllWithFilter(filter);
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

    public void removeAll() {
        expenseDAO.deleteAll();
    }

    @Override
    public void removeById(Long id) {
        expenseDAO.deleteById(id);
    }


}
