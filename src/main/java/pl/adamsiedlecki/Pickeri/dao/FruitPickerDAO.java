package pl.adamsiedlecki.Pickeri.dao;

import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FruitPickerDAO extends JpaRepository<FruitPicker,Long> {
}
