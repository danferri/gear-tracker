package dio.geartracker.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InstrumentRecord {
    private InstrumentRecordId id;
    private String description;
    private long amount;
    private Category category;

    public InstrumentRecord(String description, long amount, Category category) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        this.id = new InstrumentRecordId();
        this.description = description;
        this.amount = amount;
        this.category = category;
    }
}