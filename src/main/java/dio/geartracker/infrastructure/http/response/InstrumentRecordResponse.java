package dio.geartracker.infrastructure.http.response;

import dio.geartracker.application.output.InstrumentRecordOutput;

public record InstrumentRecordResponse(String id, String category, String description, double amount) {
    public static InstrumentRecordResponse from(InstrumentRecordOutput output) {
        return new InstrumentRecordResponse(output.id(), output.category(), output.description(), output.value());
    }
}