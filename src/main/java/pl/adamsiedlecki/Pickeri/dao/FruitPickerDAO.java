package pl.adamsiedlecki.Pickeri.dao;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;

@Repository
@Cacheable(cacheNames = "fruitPickerDAO")
public interface FruitPickerDAO extends JpaRepository<FruitPicker, Long> {

}
