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


}
