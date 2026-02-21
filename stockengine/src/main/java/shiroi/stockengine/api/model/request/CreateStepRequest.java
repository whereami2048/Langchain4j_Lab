package shiroi.stockengine.api.model.request;

import shiroi.stockengine.engine.assistants.model.AssistantType;

public record CreateStepRequest(
        String stepName,
        AssistantType assistantType,
        String prompt
) {
}
