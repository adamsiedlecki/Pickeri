package pl.adamsiedlecki.Pickeri.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import pl.adamsiedlecki.Pickeri.dao.FruitDeliveryDAO;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;
import pl.adamsiedlecki.Pickeri.entity.FruitVariety;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FruitDeliveryService {

    private FruitDeliveryDAO fruitDeliveryDAO;

    public FruitDeliveryService(FruitDeliveryDAO fruitDeliveryDAO){
        this.fruitDeliveryDAO = fruitDeliveryDAO;
    }

    public void addDelivery(FruitDelivery fruitDelivery){
        fruitDeliveryDAO.save(fruitDelivery);
    }

    public List<FruitDelivery> findAll(){
        return fruitDeliveryDAO.findAll();
    }

    public List<FruitDelivery> getDeliveriesByPickerId(long id){
        return fruitDeliveryDAO.getDeliveriesByPickerId(id);
    }

    public List<FruitDelivery> findAllWithType(String type){
        return fruitDeliveryDAO.findAllWithType(type);
    }

    public List<FruitDelivery> findAllWithVariety(String variety){
        return fruitDeliveryDAO.findAllWithVariety(variety);
    }

    public List<FruitDelivery> findByIdWithType(Long id,String type){
        return fruitDeliveryDAO.findByIdWithType(id, type);
    }

    public void removeAll(){
        fruitDeliveryDAO.deleteAll();
    }

    public List<FruitDelivery> findAllByIdVariety(Long id, String variety){
        return fruitDeliveryDAO.findAllByIdVariety(id, variety);
    }

    public long getTotalAmountOfPackages(){
        return findAll().stream().mapToLong(e->e.getPackageAmount()).sum();
    }

    public BigDecimal getPercentageParticipationForPackagesAmountByVariety(String name){
        List<FruitDelivery> allVarieties = findAll();
         List<FruitDelivery> thisVariety = findAllWithVariety(name);
         BigDecimal allAmount = new BigDecimal(0);
         for(FruitDelivery fruitDelivery: allVarieties){
             allAmount = allAmount.add(new BigDecimal(fruitDelivery.getPackageAmount()));
             //allAmount+=fruitDelivery.getPackageAmount();
         }
        BigDecimal thisAmount = new BigDecimal(0);
        for(FruitDelivery fruitDelivery: thisVariety){
            thisAmount = thisAmount.add(new BigDecimal(fruitDelivery.getPackageAmount()));
            // thisAmount+=fruitDelivery.getPackageAmount();
        }
        if(allAmount.equals(new BigDecimal(0))){
            return new BigDecimal(0);
        }
        System.out.println("all: "+allAmount+"  this: "+thisAmount);
        BigDecimal bigDecimal = thisAmount.divide(allAmount, 2, RoundingMode.FLOOR);
        System.out.println("DIVIDE RESULT: "+bigDecimal);
        BigDecimal result = bigDecimal.multiply(new BigDecimal(100));
        System.out.println("RESULT "+result);
        return result;
    }

    public Long getPackagesAmountByVariety(String name){
        List<FruitDelivery> thisVariety = findAllWithVariety(name);
        Long thisAmount = 0L;
        for(FruitDelivery fruitDelivery: thisVariety){
            thisAmount+=fruitDelivery.getPackageAmount();
        }
        return thisAmount;
    }

}
