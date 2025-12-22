package shiroi.stockengine.engine.aiservice;

import dev.langchain4j.model.chat.request.ChatRequestParameters;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import shiroi.stockengine.engine.aiservice.model.MyResponse;

@AiService
public interface GeminiAssistant {
    String chat(String message);
    MyResponse chatToMyResponse(String message);

    @SystemMessage(fromResource = "/prompts/gemini_system_prompt.md")
    @UserMessage(fromResource = "/prompts/gemini_analyze_prompt.md")
    String analyzeStock(ChatRequestParameters params);
}
