package shiroi.stockengine.engine.infrastructure.persistence.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shiroi.stockengine.engine.core.domain.StepTransition;
import shiroi.stockengine.engine.core.domain.Strategy;
import shiroi.stockengine.engine.core.repository.StepTransitionRepository;
import shiroi.stockengine.engine.infrastructure.persistence.jpa.entity.StepTransitionJpaEntity;
import shiroi.stockengine.engine.infrastructure.persistence.jpa.entity.StrategyJpaEntity;
import shiroi.stockengine.engine.infrastructure.persistence.jpa.repository.StepTransitionJpaRepository;
import shiroi.stockengine.engine.infrastructure.persistence.mapper.StepTransitionMapper;
import shiroi.stockengine.engine.infrastructure.persistence.mapper.StrategyMapper;

@Repository
@RequiredArgsConstructor
public class StepTransitionRepositoryImpl implements StepTransitionRepository {
    private final StepTransitionJpaRepository stepTransitionJpaRepository;

    private final StepTransitionMapper stepTransitionMapper;
    private final StrategyMapper strategyMapper;

    @Override
    public void create(StepTransition stepTransition) {
        StepTransitionJpaEntity stepTransitionJpaEntity = stepTransitionMapper.toStepTransitionJpaEntity(
                stepTransition);

        stepTransitionJpaRepository.save(stepTransitionJpaEntity);
    }

    @Override
    public List<StepTransition> findByStrategy(Strategy strategy) {
        StrategyJpaEntity strategyJpaEntity = strategyMapper.toStrategyJpaEntity(strategy);

        List<StepTransitionJpaEntity> findStepTransitionJpaEntities = stepTransitionJpaRepository.findByStrategy(strategyJpaEntity);
        return findStepTransitionJpaEntities.stream()
                .map(stepTransitionMapper::toStepTransition)
                .toList();
    }

    @Override
    public void update(StepTransition stepTransition) {
        StepTransitionJpaEntity stepTransitionJpaEntity = stepTransitionMapper.toStepTransitionJpaEntity(
                stepTransition);

        stepTransitionJpaRepository.save(stepTransitionJpaEntity);
    }

    @Override
    public void delete(StepTransition stepTransition) {
        StepTransitionJpaEntity stepTransitionJpaEntity = stepTransitionMapper.toStepTransitionJpaEntity(
                stepTransition);

        stepTransitionJpaRepository.delete(stepTransitionJpaEntity);
    }
}
