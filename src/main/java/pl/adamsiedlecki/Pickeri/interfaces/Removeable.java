package pl.adamsiedlecki.Pickeri.interfaces;

import pl.adamsiedlecki.Pickeri.entity.FruitPicker;

import java.util.List;

public interface Removeable <T>{

    void removeAll();

    void removeById(Long id);

    List<T> findAll();

}
