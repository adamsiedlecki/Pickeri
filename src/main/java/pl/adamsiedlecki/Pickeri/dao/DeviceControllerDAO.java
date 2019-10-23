package pl.adamsiedlecki.Pickeri.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.adamsiedlecki.Pickeri.entity.DeviceController;

@Repository
public interface DeviceControllerDAO extends JpaRepository<DeviceController, Long> {
}
