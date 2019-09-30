package pl.adamsiedlecki.Pickeri.dao;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;

import java.util.List;

@Repository
@Cacheable(cacheNames = "fruitPickerDAO")
public interface FruitPickerDAO extends JpaRepository<FruitPicker, Long> {

    @Query("SELECT p FROM FruitPicker p WHERE p.id LIKE CONCAT('%',:filter,'%') OR p.name LIKE CONCAT('%',:filter,'%') " +
            "OR p.lastName LIKE CONCAT('%',:filter,'%') OR p.gender LIKE CONCAT('%',:filter,'%') OR p.fundsPaid LIKE CONCAT('%',:filter,'%')")
    List<FruitPicker> findAllWithFilter(String filter);

}
