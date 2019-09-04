package pl.adamsiedlecki.Pickeri.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.adamsiedlecki.Pickeri.entity.FruitType;

public interface FruitTypeDAO extends JpaRepository<FruitType,Long> {

}

