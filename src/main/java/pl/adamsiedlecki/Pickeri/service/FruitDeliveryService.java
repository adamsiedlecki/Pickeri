package pl.adamsiedlecki.Pickeri.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import pl.adamsiedlecki.Pickeri.dao.FruitDeliveryDAO;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;
import pl.adamsiedlecki.Pickeri.entity.FruitVariety;

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

    public float getPercentageParticipationForPackagesAmountByVariety(String name){
        List<FruitDelivery> allVarieties = findAll();
         List<FruitDelivery> thisVariety = findAllWithVariety(name);
         float allAmount = 0;
         for(FruitDelivery fruitDelivery: allVarieties){
             allAmount+=fruitDelivery.getPackageAmount();
         }
        float thisAmount = 0;
        for(FruitDelivery fruitDelivery: thisVariety){
            thisAmount+=fruitDelivery.getPackageAmount();
        }
        if(allAmount==0){
            return 0;
        }
        return thisAmount/allAmount*100;
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
