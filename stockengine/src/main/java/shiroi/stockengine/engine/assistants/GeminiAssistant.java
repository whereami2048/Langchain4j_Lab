package shiroi.stockengine.engine.assistants;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

import dev.langchain4j.service.spring.AiService;
import shiroi.stockengine.engine.assistants.model.AssistantType;

@AiService(wiringMode = EXPLICIT, chatModel = "googleAiGeminiChatModel")
public interface GeminiAssistant extends Assistant {

    @Override
    default AssistantType getType() {
        return AssistantType.GEMINI;
    }
}
