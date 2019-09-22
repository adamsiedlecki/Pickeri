package pl.adamsiedlecki.Pickeri.service.interfaces;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;

import java.math.BigDecimal;
import java.util.List;

@Cacheable(cacheNames = "delivery")
@Service
public interface FruitDeliveryAbstract {

    BigDecimal getWeightSum();

    void removeById(Long id);

    void addDelivery(FruitDelivery fruitDelivery);

    List<FruitDelivery> findAll();

    List<FruitDelivery> getDeliveriesByPickerId(long id);

    List<FruitDelivery> findAllWithType(String type);

    List<FruitDelivery> findAllWithVariety(String variety);

    List<FruitDelivery> findByIdWithType(Long id, String type);

    void removeAll();

    List<FruitDelivery> findAllByIdVariety(Long id, String variety);

    long getTotalAmountOfPackages();

    BigDecimal getPercentageParticipationForPackagesAmountByVariety(String name);

    Long getPackagesAmountByVariety(String name);

    BigDecimal getTotalWeightByVariety(String name);

    BigDecimal getPercentageParticipationInWeight(String name);

}
