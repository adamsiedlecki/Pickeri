package pl.adamsiedlecki.Pickeri.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.adamsiedlecki.Pickeri.dao.FruitPickerDAO;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FruitPickerService {

    private FruitPickerDAO fruitPickerDAO;
    private FruitDeliveryService fruitDeliveryService;

    @Autowired
    public FruitPickerService(FruitPickerDAO fruitPickerDAO, FruitDeliveryService fruitDeliveryService){
        this.fruitPickerDAO = fruitPickerDAO;
        this.fruitDeliveryService = fruitDeliveryService;
    }

    public void addFruitPicker(FruitPicker fruitPicker){
        fruitPickerDAO.save(fruitPicker);
    }

    public List<FruitPicker> findAll(){
        List<FruitPicker> pickersList = fruitPickerDAO.findAll();
        setDeliveriesSum(pickersList);
        return pickersList;
    }

    public List<FruitPicker> findAll(String filter){
        List<FruitPicker> pickersList = fruitPickerDAO.findAll();
        Iterator<FruitPicker> iterator = pickersList.iterator();
        setAdditionalInfo(pickersList);

        while(iterator.hasNext()){
            FruitPicker fp = iterator.next();
            if(!Long.toString(fp.getId()).contains(filter)&&!fp.getName().contains(filter)&&!fp.getLastName().contains(filter)){
                iterator.remove();
            }
        }

        setDeliveriesSum(pickersList);
        return pickersList;
    }

    public long getTotalAmountOfPickers(){
        return findAll().size();
    }

    public void removeAll(){
        fruitPickerDAO.deleteAll();
    }

    private List<FruitPicker> setAdditionalInfo(List<FruitPicker> fruitPickers){

        for(FruitPicker fp : fruitPickers){
            long packagesWithTypeOne  = fruitDeliveryService.findByIdWithType(fp.getId(),"truskawka z szypułką").stream().collect(Collectors.summingLong(FruitDelivery::getPackageAmount));
            long packagesWithTypeTwo  = fruitDeliveryService.findByIdWithType(fp.getId()," truskawka bez szypułki").stream().collect(Collectors.summingLong(FruitDelivery::getPackageAmount));

            fp.setPackageDeliveryWithTypeOne(packagesWithTypeOne);
            fp.setPackageDeliveryWithTypeTwo(packagesWithTypeTwo);
        }
        return fruitPickers;
    }

    private List<FruitPicker> setDeliveriesSum(List<FruitPicker> pickersList){
        for(FruitPicker fp: pickersList){
            if(fruitDeliveryService.getDeliveriesByPickerId(fp.getId())==null){
                fp.setPackageDeliveryAmount(0);
            }else{
                List<FruitDelivery> fruitDeliveries = fruitDeliveryService.getDeliveriesByPickerId(fp.getId());
                Long sum = 0L;
                for(FruitDelivery fd: fruitDeliveries){
                    sum+=fd.getPackageAmount();
                }
                fp.setPackageDeliveryAmount(sum);

            }
        }
        return pickersList;
    }

}
