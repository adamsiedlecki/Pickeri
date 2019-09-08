package pl.adamsiedlecki.Pickeri.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;

import java.util.List;

@Repository
public interface FruitDeliveryDAO extends JpaRepository<FruitDelivery, Long> {

    @Query("SELECT (f) FROM FruitDelivery f WHERE f.fruitPickerId = ?1")
    List<FruitDelivery> getDeliveriesByPickerId(long id);

    @Query("SELECT COUNT (f.packageAmount) FROM FruitDelivery f WHERE f.fruitPickerId = ?1")
    Long getPackagesAmountForPickerId(Long id);

    @Query("SELECT (f) FROM FruitDelivery f WHERE f.type = ?1")
    List<FruitDelivery> findAllWithType(String type);

    @Query("SELECT (f) FROM FruitDelivery f WHERE f.fruitPickerId=?1 AND f.type = ?2")
    List<FruitDelivery> findByIdWithType(Long id,String type);

    @Query("SELECT (f) FROM FruitDelivery f WHERE f.fruitVariety = ?1")
    List<FruitDelivery> findAllWithVariety(String variety);

    @Query("SELECT (f) FROM FruitDelivery f WHERE f.fruitPickerId=?1 AND f.fruitVariety = ?2")
    List<FruitDelivery> findAllByIdVariety(Long id, String variety);

}
