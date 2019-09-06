package pl.adamsiedlecki.Pickeri.service;

import pl.adamsiedlecki.Pickeri.dao.FruitTypeDAO;
import pl.adamsiedlecki.Pickeri.entity.FruitType;

import java.util.ArrayList;
import java.util.List;

// This class is designed for only 4 types of fruit. List<FruitType> has only 4 FruitTypes, or null
public class FruitTypeService  {

    private FruitTypeDAO fruitTypeDAO;

    public FruitTypeService(FruitTypeDAO fruitTypeDAO){
        this.fruitTypeDAO = fruitTypeDAO;
    }

    public List<FruitType> findAll(){
        return fruitTypeDAO.findAll();
    }

    private void addFruitType(FruitType type, int slot){
        List<FruitType> types = findAll();
        fruitTypeDAO.deleteAll();
        types.remove(slot);
        types.add(slot,type);
    }

    public FruitType getType(int slot){
        List<FruitType> types = findAll();
        return types.get(slot);
    }

    public void setType(FruitType type, int slot){
        addFruitType(type,slot);
    }

}
