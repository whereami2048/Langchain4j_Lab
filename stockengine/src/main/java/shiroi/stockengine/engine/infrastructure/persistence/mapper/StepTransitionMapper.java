package shiroi.stockengine.engine.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import shiroi.stockengine.engine.core.domain.StepTransition;
import shiroi.stockengine.engine.infrastructure.persistence.jpa.entity.StepTransitionJpaEntity;

@Component
public class StepTransitionMapper {

    public StepTransition toStepTransition(StepTransitionJpaEntity stepTransitionJpaEntity) {
        return StepTransition.builder()
                .fromStep(stepTransitionJpaEntity.getFromStepJpaEntity())
                .toStep(stepTransitionJpaEntity.getToStepJpaEntity())
                .eventName(stepTransitionJpaEntity.getEventName())
                .build();
    }

    public StepTransitionJpaEntity toStepTransitionJpaEntity(StepTransition stepTransition) {
        return StepTransitionJpaEntity.builder()
                .fromStepJpaEntity(stepTransition.fromStep())
                .toStepJpaEntity(stepTransition.toStep())
                .eventName(stepTransition.eventName())
                .build();
    }
}
