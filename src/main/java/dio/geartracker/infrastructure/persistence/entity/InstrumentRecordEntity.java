package dio.geartracker.infrastructure.persistence.entity;

import dio.geartracker.domain.Category;
import dio.geartracker.domain.InstrumentRecord;
import dio.geartracker.domain.InstrumentRecordId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstrumentRecordEntity {
    @Id
    private UUID id;
    private String description;
    private long amount;

    @Enumerated(EnumType.STRING)
    private Category category;

    public static InstrumentRecordEntity from(InstrumentRecord record) {
        return new InstrumentRecordEntity(
                record.getId().uuid(),
                record.getDescription(),
                record.getAmount(),
                record.getCategory());
    }

    public InstrumentRecord toDomain() {
        return new InstrumentRecord(
                new InstrumentRecordId(this.id),
                this.description,
                this.amount,
                this.category);
    }
}