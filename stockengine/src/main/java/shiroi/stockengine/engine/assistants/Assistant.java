package shiroi.stockengine.engine.assistants;

import dev.langchain4j.service.UserMessage;
import shiroi.stockengine.engine.assistants.model.AssistantType;

public interface Assistant {
    AssistantType getType();
    String execute(@UserMessage String message);
}
