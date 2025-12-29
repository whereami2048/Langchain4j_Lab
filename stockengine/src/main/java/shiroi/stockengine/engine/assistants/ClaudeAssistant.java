package shiroi.stockengine.engine.assistants;

import shiroi.stockengine.engine.assistants.model.AssistantType;

//@AiService
public interface ClaudeAssistant extends Assistant {

    @Override
    default AssistantType getType() {
        return AssistantType.ANTHROPIC;
    }
}
