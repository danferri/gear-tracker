package dio.geartracker.domain;

import java.util.List;

public interface InstrumentRecordRepository {
    InstrumentRecord save(InstrumentRecord record);
    List<InstrumentRecord> findAllByCategory(Category category);
}