package dio.geartracker.infrastructure.persistence.repository;

import dio.geartracker.domain.Category;
import dio.geartracker.domain.InstrumentRecord;
import dio.geartracker.domain.InstrumentRecordRepository;
import dio.geartracker.infrastructure.persistence.entity.InstrumentRecordEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaInstrumentRecordRepository implements InstrumentRecordRepository {
    private final InstrumentRecordEntityRepository repository;

    public JpaInstrumentRecordRepository(InstrumentRecordEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public InstrumentRecord save(InstrumentRecord record) {
        return repository.save(InstrumentRecordEntity.from(record)).toDomain();
    }

    @Override
    public List<InstrumentRecord> findAllByCategory(Category category) {
        return repository.findAllByCategory(category).stream()
                .map(InstrumentRecordEntity::toDomain)
                .toList();
    }
}