package pl.adamsiedlecki.Pickeri.dao;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.adamsiedlecki.Pickeri.entity.Expense;

@Repository
@Cacheable(cacheNames = "expenseDAO")
public interface ExpenseDAO extends JpaRepository<Expense, Long> {
}
