package pl.adamsiedlecki.Pickeri.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import pl.adamsiedlecki.Pickeri.dao.FruitDeliveryDAO;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;

import java.util.List;

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

}
