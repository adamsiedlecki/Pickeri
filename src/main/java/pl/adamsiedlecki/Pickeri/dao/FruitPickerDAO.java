package pl.adamsiedlecki.Pickeri.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;

@Repository
public interface FruitPickerDAO extends JpaRepository<FruitPicker, Long> {

}
