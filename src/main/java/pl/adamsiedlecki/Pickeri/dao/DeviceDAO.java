package pl.adamsiedlecki.Pickeri.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.adamsiedlecki.Pickeri.entity.Device;

@Repository
public interface DeviceDAO extends JpaRepository<Device, Long> {
}
