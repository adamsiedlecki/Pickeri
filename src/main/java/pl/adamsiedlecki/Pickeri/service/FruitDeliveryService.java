package pl.adamsiedlecki.Pickeri.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.adamsiedlecki.Pickeri.dao.FruitDeliveryDAO;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;
import pl.adamsiedlecki.Pickeri.interfaces.Removeable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class FruitDeliveryService implements Removeable {

    private FruitDeliveryDAO fruitDeliveryDAO;

    public FruitDeliveryService(FruitDeliveryDAO fruitDeliveryDAO) {
        this.fruitDeliveryDAO = fruitDeliveryDAO;
    }

    public BigDecimal getWeightSum() {
        List<FruitDelivery> deliveries = findAll();
        BigDecimal sum = new BigDecimal(0);

        for (FruitDelivery delivery : deliveries) {
            if (delivery.getFruitWeight() != null) {
                sum = sum.add(delivery.getFruitWeight());
            }
        }
        return sum;
    }

    public void removeById(Long id) {
        fruitDeliveryDAO.deleteById(id);
    }

    public void addDelivery(FruitDelivery fruitDelivery) {
        fruitDeliveryDAO.save(fruitDelivery);
    }

    public List<FruitDelivery> findAll() {
        return fruitDeliveryDAO.findAll();
    }

    public List<FruitDelivery> getDeliveriesByPickerId(long id) {
        Optional<List<FruitDelivery>> optionalList = fruitDeliveryDAO.getDeliveriesByPickerId(id);
        return optionalList.orElseGet(List::of);
    }

    public List<FruitDelivery> findAllWithType(String type) {
        Optional<List<FruitDelivery>> optionalList = fruitDeliveryDAO.findAllWithType(type);
        return optionalList.orElseGet(List::of);
    }

    public List<FruitDelivery> findAllWithVariety(String variety) {
        Optional<List<FruitDelivery>> optionalList = fruitDeliveryDAO.findAllWithVariety(variety);
        return optionalList.orElseGet(List::of);
    }

    public List<FruitDelivery> findByIdWithType(Long id, String type) {
        Optional<List<FruitDelivery>> optionalList = fruitDeliveryDAO.findByIdWithType(id, type);
        return optionalList.orElseGet(List::of);
    }

    public void removeAll() {
        fruitDeliveryDAO.deleteAll();
    }

    public List<FruitDelivery> findAllByIdVariety(Long id, String variety) {
        Optional<List<FruitDelivery>> optionalList = fruitDeliveryDAO.findAllByIdVariety(id, variety);
        return optionalList.orElseGet(List::of);
    }

    public long getTotalAmountOfPackages() {
        return findAll().stream().mapToLong(FruitDelivery::getPackageAmount).sum();
    }

    @Cacheable(cacheNames = "percentageParticipationForPackagesAmountByVariety")
    public BigDecimal getPercentageParticipationForPackagesAmountByVariety(String name) {
        List<FruitDelivery> allVarieties = findAll();
        List<FruitDelivery> thisVariety = findAllWithVariety(name);
        BigDecimal allAmount = new BigDecimal(0);
        for (FruitDelivery fruitDelivery : allVarieties) {
            allAmount = allAmount.add(new BigDecimal(fruitDelivery.getPackageAmount()));
            //allAmount+=fruitDelivery.getPackageAmount();
        }
        BigDecimal thisAmount = new BigDecimal(0);
        for (FruitDelivery fruitDelivery : thisVariety) {
            thisAmount = thisAmount.add(new BigDecimal(fruitDelivery.getPackageAmount()));
        }
        if (allAmount.equals(new BigDecimal(0))) {
            return new BigDecimal(0);
        }
        BigDecimal bigDecimal = thisAmount.divide(allAmount, 4, RoundingMode.FLOOR);
        BigDecimal result = bigDecimal.multiply(new BigDecimal(100));
        result = result.stripTrailingZeros();
        return result;
    }

    @Cacheable(cacheNames = "packagesAmountByVariety")
    public Long getPackagesAmountByVariety(String name) {
        List<FruitDelivery> thisVariety = findAllWithVariety(name);
        Long thisAmount = 0L;
        for (FruitDelivery fruitDelivery : thisVariety) {
            thisAmount += fruitDelivery.getPackageAmount();
        }
        return thisAmount;
    }

    @Cacheable(cacheNames = "totalWeightByVariety")
    public BigDecimal getTotalWeightByVariety(String name) {
        List<FruitDelivery> thisVariety = findAllWithVariety(name);
        BigDecimal thisAmount = new BigDecimal(0);
        for (FruitDelivery fruitDelivery : thisVariety) {
            thisAmount = thisAmount.add(fruitDelivery.getFruitWeight());
        }
        return thisAmount;
    }

    @Cacheable(cacheNames = "getPercentageParticipationInWeight")
    public BigDecimal getPercentageParticipationInWeight(String name) {
        List<FruitDelivery> allVarieties = findAll();
        List<FruitDelivery> thisVariety = findAllWithVariety(name);
        BigDecimal allAmount = new BigDecimal(0);
        for (FruitDelivery fruitDelivery : allVarieties) {
            allAmount = allAmount.add(fruitDelivery.getFruitWeight());
            //allAmount+=fruitDelivery.getPackageAmount();
        }
        BigDecimal thisAmount = new BigDecimal(0);
        for (FruitDelivery fruitDelivery : thisVariety) {
            thisAmount = thisAmount.add(fruitDelivery.getFruitWeight());
        }
        if (allAmount.equals(new BigDecimal(0))) {
            return new BigDecimal(0);
        }
        BigDecimal bigDecimal = thisAmount.divide(allAmount, 4, RoundingMode.FLOOR);
        BigDecimal result = bigDecimal.multiply(new BigDecimal(100));
        result = result.stripTrailingZeros();
        return result;
    }

}
