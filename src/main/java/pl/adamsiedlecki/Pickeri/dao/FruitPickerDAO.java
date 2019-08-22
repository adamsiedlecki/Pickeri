package pl.adamsiedlecki.Pickeri.dao;

import org.springframework.stereotype.Repository;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface FruitPickerDAO extends JpaRepository<FruitPicker,Long> {

}
