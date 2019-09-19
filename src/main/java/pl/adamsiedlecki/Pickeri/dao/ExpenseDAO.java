package pl.adamsiedlecki.Pickeri.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.adamsiedlecki.Pickeri.entity.Expense;

@Repository
public interface ExpenseDAO extends JpaRepository<Expense, Long> {
}
