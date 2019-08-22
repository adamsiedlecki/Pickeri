package pl.adamsiedlecki.Pickeri.dao;

import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FruitDeliveryDAO extends JpaRepository<FruitDelivery, Long> {
}
