package pl.adamsiedlecki.Pickeri.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.adamsiedlecki.Pickeri.entity.FruitVariety;

@Repository
public interface FruitVarietyDAO extends JpaRepository<FruitVariety,Long> {
}
