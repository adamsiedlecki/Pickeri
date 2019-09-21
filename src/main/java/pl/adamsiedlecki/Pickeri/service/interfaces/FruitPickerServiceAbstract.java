package pl.adamsiedlecki.Pickeri.service.interfaces;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

@Service
@Cacheable(cacheNames = "fruitPicker")
public interface FruitPickerServiceAbstract {

     void addFruitPicker(FruitPicker fruitPicker);

     List<FruitPicker> findAll();

     List<FruitPicker> findAll(String filter);

     long getTotalAmountOfPickers();

     void removeAll();



}
