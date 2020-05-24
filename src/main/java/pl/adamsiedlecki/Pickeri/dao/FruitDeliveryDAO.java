package pl.adamsiedlecki.Pickeri.dao;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;

import java.util.List;
import java.util.Optional;

@Repository
@Cacheable(cacheNames = "fruitDeliveryDAO")
public interface FruitDeliveryDAO extends JpaRepository<FruitDelivery, Long> {

    @Query("SELECT (f) FROM FruitDelivery f WHERE YEAR(f.deliveryTime) = YEAR(CURRENT_DATE()) AND MONTH(f.deliveryTime) = MONTH(CURRENT_DATE()) AND DAY(f.deliveryTime) = DAY(CURRENT_DATE()) ")
    Optional<List<FruitDelivery>> getTodayDeliveries();

    @Query("SELECT (f) FROM FruitDelivery f WHERE f.fruitPickerId = ?1")
    Optional<List<FruitDelivery>> getDeliveriesByPickerId(long id);

    @Query("SELECT (f) FROM FruitDelivery f WHERE f.type = ?1")
    Optional<List<FruitDelivery>> findAllWithType(String type);

    @Query("SELECT (f) FROM FruitDelivery f WHERE f.fruitPickerId=?1 AND f.type = ?2")
    Optional<List<FruitDelivery>> findByIdWithType(Long id, String type);

    @Query("SELECT (f) FROM FruitDelivery f WHERE f.fruitVarietyName = ?1")
    Optional<List<FruitDelivery>> findAllWithVariety(String variety);

    @Query("SELECT (f) FROM FruitDelivery f WHERE f.fruitPickerId=?1 AND f.fruitVarietyName = ?2")
    Optional<List<FruitDelivery>> findAllByIdVariety(Long id, String variety);

}
