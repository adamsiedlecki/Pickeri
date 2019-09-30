package pl.adamsiedlecki.Pickeri.dao;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.adamsiedlecki.Pickeri.entity.Expense;

import java.util.List;

@Repository
@Cacheable(cacheNames = "expenseDAO")
public interface ExpenseDAO extends JpaRepository<Expense, Long> {

    @Query("SELECT e FROM Expense e WHERE e.id LIKE CONCAT('%',:filter,'%') OR e.name LIKE CONCAT('%',:filter,'%') " +
            "OR e.time LIKE CONCAT('%',:filter,'%') OR e.moneyAmount LIKE CONCAT('%',:filter,'%')")
    List<Expense> findAllWithFilter(String filter);
}
