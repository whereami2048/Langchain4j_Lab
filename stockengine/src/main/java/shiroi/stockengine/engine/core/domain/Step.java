package shiroi.stockengine.engine.core.domain;

import lombok.Builder;
import shiroi.stockengine.engine.assistants.model.AssistantType;

@Builder
public record Step(
    String stepName,
    StepType stepType,
    AssistantType assistantType,
    String prompt
) {
}
