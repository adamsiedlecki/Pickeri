package pl.adamsiedlecki.Pickeri.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.adamsiedlecki.Pickeri.entity.SettingsEntity;

import javax.persistence.Cacheable;

@Repository
@Cacheable
public interface SettingsDAO extends JpaRepository<SettingsEntity, String> {
}
