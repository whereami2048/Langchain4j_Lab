package shiroi.stockengine.api.model.request;

import shiroi.stockengine.engine.assistants.model.AssistantType;
import shiroi.stockengine.engine.core.domain.StepType;

public record CreateStepRequest(
        String stepName,
        StepType stepType,
        AssistantType assistantType,
        String prompt,
) {
}
