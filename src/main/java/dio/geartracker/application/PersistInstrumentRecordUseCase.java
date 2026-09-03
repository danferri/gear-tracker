package dio.geartracker.application;

import dio.geartracker.application.input.PersistInstrumentRecordInput;
import dio.geartracker.application.output.InstrumentRecordOutput;
import dio.geartracker.domain.InstrumentRecord;
import dio.geartracker.domain.InstrumentRecordRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class PersistInstrumentRecordUseCase {
    private final InstrumentRecordRepository repository;

    public PersistInstrumentRecordUseCase(InstrumentRecordRepository repository) {
        this.repository = repository;
    }

    @Tool(name = "persist-instrument-record", description = "Persists a new instrument, customization or maintenance record")
    public InstrumentRecordOutput execute(PersistInstrumentRecordInput input) {
        var record = repository.save(
                new InstrumentRecord(input.description(), input.amount(), input.category()));
        return InstrumentRecordOutput.from(record);
    }
}