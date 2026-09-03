package dio.geartracker.application.input;

import dio.geartracker.domain.Category;
import org.springframework.ai.tool.annotation.ToolParam;

public record PersistInstrumentRecordInput(
        @ToolParam(description = "Description of the instrument or service") String description,
        @ToolParam(description = "Value in cents") long amount,
        @ToolParam(description = "Category of the record") Category category) {
}