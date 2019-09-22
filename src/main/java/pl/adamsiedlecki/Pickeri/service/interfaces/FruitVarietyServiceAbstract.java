package pl.adamsiedlecki.Pickeri.service.interfaces;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.adamsiedlecki.Pickeri.entity.FruitVariety;
import java.util.List;

@Service
@Cacheable(cacheNames = "fruitVariety")
public interface FruitVarietyServiceAbstract {

    List<FruitVariety> findAll();

    void save(FruitVariety fruitVariety);

    List<String> getVarietiesNames();

}
