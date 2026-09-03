package dio.geartracker.application;

import dio.geartracker.domain.Category;
import dio.geartracker.domain.InstrumentRecord;
import dio.geartracker.domain.InstrumentRecordRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class CalculateTotalByCategoryUseCase {
    private final InstrumentRecordRepository repository;

    public CalculateTotalByCategoryUseCase(InstrumentRecordRepository repository) {
        this.repository = repository;
    }

    @Tool(name = "calculate-total-by-category", description = "Calculates the total amount spent in a specific category")
    public String execute(
            @ToolParam(description = "Category to calculate the total for") Category category) {
        long total = repository.findAllByCategory(category).stream()
                .mapToLong(InstrumentRecord::getAmount)
                .sum();
        return "The total for category " + category + " is " + total + " cents.";
    }
}