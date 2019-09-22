package pl.adamsiedlecki.Pickeri.dao;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.adamsiedlecki.Pickeri.entity.FruitVariety;

import java.util.List;

@Repository
@Cacheable(cacheNames = "fruitVarietyDAO")
public interface FruitVarietyDAO extends JpaRepository<FruitVariety, Long> {

    @Query("SELECT name FROM FruitVariety fv WHERE fv.name!=NULL ")
    List<String> getVarietiesNames();
}
