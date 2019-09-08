package pl.adamsiedlecki.Pickeri.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.adamsiedlecki.Pickeri.entity.FruitType;

public interface FruitTypeDAO extends JpaRepository<FruitType,Long> {

    @Query("SELECT (ft) FROM FruitType ft WHERE ft.slot=?1")
    FruitType getBySlot(Integer slot);

}

