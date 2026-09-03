package dio.geartracker.application.output;

import dio.geartracker.domain.InstrumentRecord;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record InstrumentRecordOutput(String id, String description, String category, double value) {
    public static InstrumentRecordOutput from(InstrumentRecord record) {
        return new InstrumentRecordOutput(
                record.getId().uuid().toString(),
                record.getDescription(),
                record.getCategory().name(),
                BigDecimal.valueOf(record.getAmount()).setScale(2, RoundingMode.HALF_UP).doubleValue());
    }
}
