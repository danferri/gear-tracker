package dio.geartracker.application;

import dio.geartracker.application.output.InstrumentRecordOutput;
import dio.geartracker.domain.Category;
import dio.geartracker.domain.InstrumentRecordRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListRecordsByCategoryUseCase {
    private final InstrumentRecordRepository repository;

    public ListRecordsByCategoryUseCase(InstrumentRecordRepository repository) {
        this.repository = repository;
    }

    @Tool(name = "list-records-by-category", description = "Lists all records filtered by a given category")
    public List<InstrumentRecordOutput> execute(
            @ToolParam(description = "Category to filter by") Category category) {
        return repository.findAllByCategory(category).stream()
                .map(InstrumentRecordOutput::from)
                .toList();
    }
}