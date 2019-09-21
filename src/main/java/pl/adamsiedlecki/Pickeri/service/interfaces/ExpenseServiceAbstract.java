package pl.adamsiedlecki.Pickeri.service.interfaces;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.adamsiedlecki.Pickeri.entity.Expense;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Cacheable(cacheNames = "expense")
@Service
public interface ExpenseServiceAbstract {

     void save(Expense expense);

     void delete(Expense expense);

     Expense getExpenseById(Long id);

     List<Expense> findAll();

     List<Expense> findAll(String filter);

     BigDecimal getTotalAmountOfSpentMoney();

    BigDecimal getAverageAmountOfSpentMoney();

}
