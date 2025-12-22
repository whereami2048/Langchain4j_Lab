package shiroi.stockengine.engine.aiservice;

import dev.langchain4j.service.spring.AiService;

@AiService
public interface GPTAssistant {
    String chat(String message);
}
