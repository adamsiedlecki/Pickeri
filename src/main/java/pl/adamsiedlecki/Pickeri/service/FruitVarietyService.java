package pl.adamsiedlecki.Pickeri.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.adamsiedlecki.Pickeri.dao.FruitVarietyDAO;
import pl.adamsiedlecki.Pickeri.entity.FruitVariety;
import pl.adamsiedlecki.Pickeri.interfaces.Removeable;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FruitVarietyService implements Removeable {

    private FruitVarietyDAO fruitVarietyDAO;
    private FruitDeliveryService fruitDeliveryService;

    @Autowired
    public FruitVarietyService(FruitVarietyDAO fruitVarietyDAO, FruitDeliveryService fruitDeliveryService) {
        this.fruitVarietyDAO = fruitVarietyDAO;
        this.fruitDeliveryService = fruitDeliveryService;
        if(fruitVarietyDAO.findAll().size()==0){
            fruitVarietyDAO.save(new FruitVariety("ALBA", ""));
            fruitVarietyDAO.save(new FruitVariety("MARMOLADA", ""));
        }
    }

    public List<FruitVariety> findAll() {
        List<FruitVariety> all = fruitVarietyDAO.findAll();
        setPercentageParticipationInPackagesAmount(all);
        setPackagesAmount(all);
        setTotalWeight(all);
        setPercentageParticipationInWeight(all);
        return all;
    }

    public void save(FruitVariety fruitVariety) {
        fruitVarietyDAO.save(fruitVariety);
    }

    private void setPercentageParticipationInPackagesAmount(List<FruitVariety> fruitVarieties) {
        for (FruitVariety fv : fruitVarieties) {
            BigDecimal percenatage = fruitDeliveryService.getPercentageParticipationForPackagesAmountByVariety(fv.getName());
            fv.setPercentageParticipationInPackagesAmount(percenatage);
        }
    }

    private void setPackagesAmount(List<FruitVariety> fruitVarieties) {
        for (FruitVariety fv : fruitVarieties) {
            long amount = fruitDeliveryService.getPackagesAmountByVariety(fv.getName());
            fv.setTotalPackages(amount);
        }
    }

    private void setTotalWeight(List<FruitVariety> fruitVarieties) {
        for (FruitVariety fv : fruitVarieties) {
            BigDecimal amount = fruitDeliveryService.getTotalWeightByVariety(fv.getName());
            fv.setTotalWeight(amount);
        }
    }

    private void setPercentageParticipationInWeight(List<FruitVariety> fruitVarieties) {
        for (FruitVariety fv : fruitVarieties) {
            BigDecimal percenatage = fruitDeliveryService.getPercentageParticipationInWeight(fv.getName());
            fv.setPercentageParticipationInWeight(percenatage);
        }
    }

    @Override
    public void removeAll() {
        fruitVarietyDAO.deleteAll();
    }

    @Override
    public void removeById(Long id) {
        fruitVarietyDAO.deleteById(id);
    }

    public List<String> getVarietiesNames() {
        return fruitVarietyDAO.getVarietiesNames().orElseGet(List::of);
    }

}
