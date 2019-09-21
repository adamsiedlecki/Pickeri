package pl.adamsiedlecki.Pickeri.service.interfaces;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;

import java.math.BigDecimal;
import java.util.List;

@Cacheable(cacheNames = "delivery")
@Service
public interface FruitDeliveryAbstract {

    @Cacheable(cacheNames = "getWeightSum")
    BigDecimal getWeightSum();

    void removeById(Long id);

    void addDelivery(FruitDelivery fruitDelivery);

    @Cacheable(cacheNames = "findAll")
    List<FruitDelivery> findAll();

    @Cacheable(cacheNames = "getDeliveriesByPickerId")
    List<FruitDelivery> getDeliveriesByPickerId(long id);

    List<FruitDelivery> findAllWithType(String type);

    @Cacheable(cacheNames = "findAllWithVariety")
    List<FruitDelivery> findAllWithVariety(String variety);

    @Cacheable(cacheNames = "findByIdWithType")
    List<FruitDelivery> findByIdWithType(Long id, String type);

    void removeAll();

    @Cacheable(cacheNames = "findAllByIdVariety")
    List<FruitDelivery> findAllByIdVariety(Long id, String variety);

    @Cacheable(cacheNames = "getTotalAmountOfPackages")
    long getTotalAmountOfPackages();

    @Cacheable(cacheNames = "getPercentageParticipationForPackagesAmountByVariety")
    BigDecimal getPercentageParticipationForPackagesAmountByVariety(String name);

    @Cacheable(cacheNames = "getPackagesAmountByVariety")
    Long getPackagesAmountByVariety(String name);

    @Cacheable(cacheNames = "getTotalWeightByVariety")
    BigDecimal getTotalWeightByVariety(String name);

    @Cacheable(cacheNames = "getPercentageParticipationInWeight")
    BigDecimal getPercentageParticipationInWeight(String name);



}
