package shiroi.stockengine.engine.aiservice;

import dev.langchain4j.service.spring.AiService;

@AiService
public interface ClaudeAssistant {
    String chat(String message);
}
