package dio.geartracker.domain;

import java.util.UUID;

public record InstrumentRecordId(UUID uuid) {
    public InstrumentRecordId() {
        this(UUID.randomUUID());
    }
}
