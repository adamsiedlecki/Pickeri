package pl.adamsiedlecki.Pickeri.dao;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.adamsiedlecki.Pickeri.entity.WorkTime;

@Repository
@Cacheable
public interface WorkTimeDAO extends JpaRepository<WorkTime, Long> {
}
