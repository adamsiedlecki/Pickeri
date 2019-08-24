package pl.adamsiedlecki.Pickeri.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.adamsiedlecki.Pickeri.dao.FruitVarietyDAO;
import pl.adamsiedlecki.Pickeri.entity.FruitVariety;

import java.util.List;

@Service
public class FruitVarietyService {

    private FruitVarietyDAO fruitVarietyDAO;
    private FruitDeliveryService fruitDeliveryService;

    @Autowired
    public FruitVarietyService(FruitVarietyDAO fruitVarietyDAO, FruitDeliveryService fruitDeliveryService){
        this.fruitVarietyDAO = fruitVarietyDAO;
        this.fruitDeliveryService = fruitDeliveryService;
    }

    public List<FruitVariety> findAll(){
        List<FruitVariety> all = fruitVarietyDAO.findAll();
        setPercentageParticipationInPackagesAmount(all);
        setPackagesAmount(all);
        return all;
    }

    public void save(FruitVariety fruitVariety){
        fruitVarietyDAO.save(fruitVariety);
    }

    private List<FruitVariety> setPercentageParticipationInPackagesAmount(List<FruitVariety> fruitVarieties){
        for(FruitVariety fv : fruitVarieties){
            float percenatage = fruitDeliveryService.getPercentageParticipationForPackagesAmountByVariety(fv.getName());
            fv.setPercentageParticipationInPackagesAmount(percenatage);
        }
        return fruitVarieties;
    }

    private List<FruitVariety> setPackagesAmount(List<FruitVariety> fruitVarieties){
        for(FruitVariety fv : fruitVarieties){
            long amount = fruitDeliveryService.getPackagesAmountByVariety(fv.getName());
            fv.setTotalPackages(amount);
        }
        return fruitVarieties;
    }

}
