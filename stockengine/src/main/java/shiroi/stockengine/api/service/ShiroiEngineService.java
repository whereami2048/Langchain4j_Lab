package shiroi.stockengine.api.service;

import dev.langchain4j.model.chat.request.ChatRequestParameters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shiroi.stockengine.engine.aiservice.GeminiAssistant;

@Service
@RequiredArgsConstructor
public class ShiroiEngineService {
    private final GeminiAssistant geminiAssistant;

    public String getHelloWorld() {
        return geminiAssistant.chat("Hello World");
    }

    public String analyzeStock() {
        ChatRequestParameters params = ChatRequestParameters.builder().build();
        return geminiAssistant.analyzeStock(params);
    }
}
