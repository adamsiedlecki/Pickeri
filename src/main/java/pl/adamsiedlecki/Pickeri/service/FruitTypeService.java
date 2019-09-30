package pl.adamsiedlecki.Pickeri.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.adamsiedlecki.Pickeri.dao.FruitTypeDAO;
import pl.adamsiedlecki.Pickeri.entity.FruitType;
import pl.adamsiedlecki.Pickeri.exceptions.NotUniqueTypesException;

import java.util.List;

// This class is designed for only 4 types of fruit. List<FruitType> has only 4 FruitTypes, or null
@Service
public class FruitTypeService {

    private FruitTypeDAO fruitTypeDAO;

    @Autowired
    public FruitTypeService(FruitTypeDAO fruitTypeDAO) {
        this.fruitTypeDAO = fruitTypeDAO;
    }

    public List<FruitType> findAll() {
        return fruitTypeDAO.findAll();
    }

    public FruitType getType(int slot) {
        return fruitTypeDAO.getBySlot(slot).orElseGet(FruitType::new);
    }

    public List<String> getTypeNames() {
        return fruitTypeDAO.getTypeNames().orElseGet(List::of);
    }

    public void addTypes(List<FruitType> typesList) throws NotUniqueTypesException {
        if (hasDifferentNames(typesList)) {
            fruitTypeDAO.deleteAll();
            fruitTypeDAO.saveAll(typesList);
        } else {
            throw new NotUniqueTypesException();
        }
    }

    public int getTypeAmount() {
        List<String> names = getTypeNames();
        return names.size();
    }

    private boolean hasDifferentNames(List<FruitType> fruitTypeList) {
        for (FruitType ft : fruitTypeList) {
            for (FruitType ftt : fruitTypeList) {
                if (nameIsNotEmpty(ft, ftt)) {
                    if (ft != ftt) {
                        if (ft.getName().equals(ftt.getName())) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean nameIsNotEmpty(FruitType ft1, FruitType ft2) {
        return ft1.getName() != null && ft2 != null;
    }

}
