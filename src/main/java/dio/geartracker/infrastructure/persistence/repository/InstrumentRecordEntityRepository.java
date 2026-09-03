package dio.geartracker.infrastructure.persistence.repository;

import dio.geartracker.domain.Category;
import dio.geartracker.infrastructure.persistence.entity.InstrumentRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InstrumentRecordEntityRepository extends JpaRepository<InstrumentRecordEntity, UUID> {
    List<InstrumentRecordEntity> findAllByCategory(Category category);
}