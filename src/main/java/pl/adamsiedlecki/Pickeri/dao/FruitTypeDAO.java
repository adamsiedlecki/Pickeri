package pl.adamsiedlecki.Pickeri.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.adamsiedlecki.Pickeri.entity.FruitType;

import java.util.List;

public interface FruitTypeDAO extends JpaRepository<FruitType, Long> {

    @Query("SELECT (ft) FROM FruitType ft WHERE ft.slot=?1")
    FruitType getBySlot(Integer slot);

    @Query("SELECT name FROM FruitType ft WHERE ft.name!=NULL")
    List<String> getTypeNames();

}

