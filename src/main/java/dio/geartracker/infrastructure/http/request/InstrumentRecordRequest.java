package dio.geartracker.infrastructure.http.request;

import dio.geartracker.application.input.PersistInstrumentRecordInput;
import dio.geartracker.domain.Category;

public record InstrumentRecordRequest(String description, Category category, long amount) {
    public PersistInstrumentRecordInput toInput() {
        return new PersistInstrumentRecordInput(description, amount, category);
    }
}