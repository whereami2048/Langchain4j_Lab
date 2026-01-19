package shiroi.stockengine.engine.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import shiroi.stockengine.engine.core.domain.Step;
import shiroi.stockengine.engine.infrastructure.persistence.jpa.entity.StepJpaEntity;

@Component
public class StepMapper {
    public Step toStep(StepJpaEntity stepJpaEntity) {
        return Step.builder()
                .stepName(stepJpaEntity.getStepName())
                .type(stepJpaEntity.getStepType())
                .assistantType(stepJpaEntity.getAssistantType())
                .prompt(stepJpaEntity.getPrompt())
                .build();
    }

    public StepJpaEntity toStepJpaEntity(Step step) {
        return StepJpaEntity.builder()
                .stepName(step.stepName())
                .stepType(step.stepType())
                .assistantType(step.assistantType())
                .prompt(step.prompt())
                .build();
    }
}
