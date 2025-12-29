package shiroi.stockengine.engine.assistants;

import shiroi.stockengine.engine.assistants.model.AssistantType;

//@AiService(wiringMode = EXPLICIT, chatModel = "openAiChatModel")
public interface OpenAiAssistant extends Assistant{

    @Override
    default AssistantType getType() {
        return AssistantType.OPENAI;
    }
}
