package pl.adamsiedlecki.Pickeri.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface FruitDeliveryDAO extends JpaRepository<FruitDelivery, Long> {

    @Query("SELECT (f) FROM FruitDelivery f WHERE f.fruitPickerId = id")
    List<FruitDelivery> getDeliveriesByPickerId(Long id);

    @Query("SELECT COUNT (f.packageAmount) FROM FruitDelivery f WHERE f.fruitPickerId = id")
    Long getPackagesAmountForPickerId(Long id);

}
