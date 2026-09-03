package dio.geartracker.infrastructure.http;

import dio.geartracker.application.CalculateTotalByCategoryUseCase;
import dio.geartracker.application.ListRecordsByCategoryUseCase;
import dio.geartracker.application.PersistInstrumentRecordUseCase;
import dio.geartracker.domain.Category;
import dio.geartracker.infrastructure.http.request.InstrumentRecordRequest;
import dio.geartracker.infrastructure.http.response.InstrumentRecordResponse;
import org.springframework.ai.audio.transcription.TranscriptionModel;
import org.springframework.ai.audio.tts.TextToSpeechModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@RestController
@RequestMapping("/records")
public class InstrumentRecordController {
    private final PersistInstrumentRecordUseCase persistUseCase;
    private final ListRecordsByCategoryUseCase listUseCase;
    private final CalculateTotalByCategoryUseCase calculateUseCase;
    private final TranscriptionModel transcriptionModel;
    private final ChatClient chatClient;
    private final TextToSpeechModel textToSpeechModel;

    public InstrumentRecordController(
            PersistInstrumentRecordUseCase persistUseCase,
            ListRecordsByCategoryUseCase listUseCase,
            CalculateTotalByCategoryUseCase calculateUseCase,
            TranscriptionModel transcriptionModel,
            @Value("classpath:prompts/system-message.st") Resource systemPrompt,
            ChatClient.Builder chatClientBuilder,
            TextToSpeechModel textToSpeechModel) throws IOException {

        this.persistUseCase = persistUseCase;
        this.listUseCase = listUseCase;
        this.calculateUseCase = calculateUseCase;
        this.transcriptionModel = transcriptionModel;
        this.textToSpeechModel = textToSpeechModel;

        this.chatClient = chatClientBuilder
                .defaultSystem(systemPrompt.getContentAsString(Charset.defaultCharset()))
                .defaultTools(persistUseCase, listUseCase, calculateUseCase)
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InstrumentRecordResponse create(@RequestBody InstrumentRecordRequest request) {
        return InstrumentRecordResponse.from(persistUseCase.execute(request.toInput()));
    }

    @GetMapping("/{category}")
    public List<InstrumentRecordResponse> listByCategory(@PathVariable Category category) {
        return listUseCase.execute(category).stream()
                .map(InstrumentRecordResponse::from)
                .toList();
    }

    @PostMapping(value = "/ai", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "audio/mp3")
    public ResponseEntity<Resource> transcribe(@RequestParam("file") MultipartFile file) {
        var userMessage = transcriptionModel.transcribe(file.getResource());
        var result = chatClient.prompt().user(userMessage).call().content();

        byte[] audio = textToSpeechModel.call(result);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("response.mp3")
                                .build()
                                .toString())
                .body(new ByteArrayResource(audio));
    }
}