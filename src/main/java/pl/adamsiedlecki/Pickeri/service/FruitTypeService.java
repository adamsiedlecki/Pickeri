package pl.adamsiedlecki.Pickeri.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.adamsiedlecki.Pickeri.dao.FruitTypeDAO;
import pl.adamsiedlecki.Pickeri.entity.FruitType;
import java.util.List;

// This class is designed for only 4 types of fruit. List<FruitType> has only 4 FruitTypes, or null
@Service
public class FruitTypeService  {

    private FruitTypeDAO fruitTypeDAO;

    @Autowired
    public FruitTypeService(FruitTypeDAO fruitTypeDAO) {
        this.fruitTypeDAO = fruitTypeDAO;
    }

    public List<FruitType> findAll() {
        return fruitTypeDAO.findAll();
    }

    public FruitType getType(int slot) {
        if (fruitTypeDAO.getBySlot(slot) == null) {
            return new FruitType();
        }
        return fruitTypeDAO.getBySlot(slot);
    }

    public List<String> getTypeNames() {
        return fruitTypeDAO.getTypeNames();
    }

    public void addTypes(List<FruitType> types) throws Exception {

        for (FruitType ft : types) {
            for (FruitType ftt : types) {
                if (ft.getSlot() != -1 || ftt.getSlot() != -1) {
                    if (ft != ftt) {
                        if (ft.getSlot().equals(ftt.getSlot())) {
                            throw new Exception("There are 2 or more types in the list with the same slot!");
                        }
                    }
                }
            }
        }

        fruitTypeDAO.deleteAll();
        fruitTypeDAO.saveAll(types);
    }

}
