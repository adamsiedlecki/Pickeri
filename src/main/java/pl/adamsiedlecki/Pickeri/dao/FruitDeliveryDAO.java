package pl.adamsiedlecki.Pickeri.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;

import java.util.List;

@Repository
public interface FruitDeliveryDAO extends JpaRepository<FruitDelivery, Long> {

    @Query("SELECT (f) FROM FruitDelivery f WHERE f.fruitPickerId = ?1")
    List<FruitDelivery> getDeliveriesByPickerId(long id);

    @Query("SELECT (f) FROM FruitDelivery f WHERE f.type = ?1")
    List<FruitDelivery> findAllWithType(String type);

    @Query("SELECT (f) FROM FruitDelivery f WHERE f.fruitPickerId=?1 AND f.type = ?2")
    List<FruitDelivery> findByIdWithType(Long id, String type);

    @Query("SELECT (f) FROM FruitDelivery f WHERE f.fruitVarietyName = ?1")
    List<FruitDelivery> findAllWithVariety(String variety);

    @Query("SELECT (f) FROM FruitDelivery f WHERE f.fruitPickerId=?1 AND f.fruitVarietyName = ?2")
    List<FruitDelivery> findAllByIdVariety(Long id, String variety);

}
