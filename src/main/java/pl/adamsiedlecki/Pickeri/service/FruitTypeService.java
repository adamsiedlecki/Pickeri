package pl.adamsiedlecki.Pickeri.service;

import pl.adamsiedlecki.Pickeri.dao.FruitTypeDAO;
import pl.adamsiedlecki.Pickeri.entity.FruitType;

import java.util.List;

public class FruitTypeService  {

    private FruitTypeDAO fruitTypeDAO;

    public FruitTypeService(FruitTypeDAO fruitTypeDAO){
        this.fruitTypeDAO = fruitTypeDAO;
    }

    public List<FruitType> findAll(){
        return fruitTypeDAO.findAll();
    }

}
