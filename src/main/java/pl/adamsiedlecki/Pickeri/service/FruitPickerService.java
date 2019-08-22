package pl.adamsiedlecki.Pickeri.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.adamsiedlecki.Pickeri.dao.FruitDeliveryDAO;
import pl.adamsiedlecki.Pickeri.dao.FruitPickerDAO;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;

import java.util.List;

@Service
public class FruitPickerService {

    private FruitPickerDAO fruitPickerDAO;
    private FruitDeliveryDAO fruitDeliveryDAO;

    @Autowired
    public FruitPickerService(FruitPickerDAO fruitPickerDAO, FruitDeliveryDAO fruitDeliveryDAO){
        this.fruitPickerDAO = fruitPickerDAO;
        this.fruitDeliveryDAO = fruitDeliveryDAO;
    }

    public void addFruitPicker(FruitPicker fruitPicker){
        fruitPickerDAO.save(fruitPicker);
    }

    public List<FruitPicker> findAll(){
        List<FruitPicker> pickersList = fruitPickerDAO.findAll();

        for(FruitPicker fp: pickersList){
            if(fruitDeliveryDAO.getDeliveriesByPickerId(fp.getId())==null){
                fp.setPackageDeliveryAmount(0);
            }else{
                List<FruitDelivery> fruitDeliveries = fruitDeliveryDAO.getDeliveriesByPickerId(fp.getId());
                Long sum = 0L;
                for(FruitDelivery fd: fruitDeliveries){
                    sum+=fd.getPackageAmount();
                    System.out.println(fd.getFruitVariety());
                }
                fp.setPackageDeliveryAmount(sum);

            }
        }

        return pickersList;
    }

}
