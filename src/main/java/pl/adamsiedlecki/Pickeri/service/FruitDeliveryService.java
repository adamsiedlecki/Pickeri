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
        return fruitDeliveryDAO.findAllWithType(variety);
    }

    public List<FruitDelivery> findByIdWithType(Long id,String type){
        return fruitDeliveryDAO.findByIdWithType(id, type);
    }

    public List<FruitDeliveryDAO> findAllByIdVariety(Long id, String variety){
        return fruitDeliveryDAO.findAllByIdVariety(id, variety);
    }

    public long getTotalAmountOfPackages(){
        return findAll().stream().mapToLong(e->e.getPackageAmount()).sum();
    }

    public Long getPercentageParticipationForPackagesAmountByVariety(String name){
        List<FruitDelivery> allVarieties = findAll();
         List<FruitDelivery> thisVariety = findAllWithVariety(name);
         Long allAmount = 0L;
         for(FruitDelivery fruitDelivery: allVarieties){
             allAmount+=fruitDelivery.getPackageAmount();
         }
        Long thisAmount = 0L;
        for(FruitDelivery fruitDelivery: thisVariety){
            thisAmount+=fruitDelivery.getPackageAmount();
        }
        return thisAmount/allAmount;
    }

}
