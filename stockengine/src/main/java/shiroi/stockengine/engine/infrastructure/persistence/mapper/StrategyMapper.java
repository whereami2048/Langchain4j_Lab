package shiroi.stockengine.engine.infrastructure.persistence.mapper;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shiroi.stockengine.engine.core.domain.Step;
import shiroi.stockengine.engine.core.domain.StepTransition;
import shiroi.stockengine.engine.core.domain.Strategy;
import shiroi.stockengine.engine.infrastructure.persistence.jpa.entity.StepJpaEntity;
import shiroi.stockengine.engine.infrastructure.persistence.jpa.entity.StepTransitionJpaEntity;
import shiroi.stockengine.engine.infrastructure.persistence.jpa.entity.StrategyJpaEntity;

@Component
@RequiredArgsConstructor
public class StrategyMapper {

    private final StepMapper stepMapper;
    private final StepTransitionMapper stepTransitionMapper;

    public Strategy toStrategy(StrategyJpaEntity strategyJpaEntity) {
        List<Step> steps = strategyJpaEntity.getStepJpaEntities().stream()
                .map(stepMapper::toStep).toList();

        List<StepTransition> stepTransitions = strategyJpaEntity.getStepTransitionJpaEntities().stream()
                .map(stepTransitionMapper::toStepTransition).toList();

        return Strategy.builder()
                .strategyName(strategyJpaEntity.getStrategyName())
                .steps(steps)
                .stepTransitions(stepTransitions)
                .build();
    }

    public StrategyJpaEntity toStrategyJpaEntity(Strategy strategy) {
        List<StepJpaEntity> stepJpaEntities = strategy.steps().stream()
                .map(stepMapper::toStepJpaEntity).toList();

        List<StepTransitionJpaEntity> stepTransitionJpaEntities = strategy.stepTransitions().stream()
                .map(stepTransitionMapper::toStepTransitionJpaEntity).toList();

        return StrategyJpaEntity.builder()
                .strategyName(strategy.strategyName())
                .stepJpaEntities(stepJpaEntities)
                .stepTransitionJpaEntities(stepTransitionJpaEntities)
                .build();
    }
}
