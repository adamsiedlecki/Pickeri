package pl.adamsiedlecki.Pickeri.service.interfaces;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.adamsiedlecki.Pickeri.entity.FruitType;

import java.util.List;

@Service
@Cacheable(cacheNames = "fruitType")
public interface FruitTypeServiceAbstract {

    public FruitType getType(int slot);

    public List<String> getTypeNames();

    public void addTypes(List<FruitType> types) throws Exception;


}
