package shiroi.stockengine.engine.infrastructure.persistence.repository;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shiroi.stockengine.engine.core.domain.Step;
import shiroi.stockengine.engine.core.domain.Strategy;
import shiroi.stockengine.engine.core.repository.StepRepository;
import shiroi.stockengine.engine.infrastructure.persistence.jpa.entity.StepJpaEntity;
import shiroi.stockengine.engine.infrastructure.persistence.jpa.entity.StrategyJpaEntity;
import shiroi.stockengine.engine.infrastructure.persistence.jpa.repository.StepJpaRepository;
import shiroi.stockengine.engine.infrastructure.persistence.mapper.StepMapper;
import shiroi.stockengine.engine.infrastructure.persistence.mapper.StrategyMapper;

@Repository
@RequiredArgsConstructor
public class StepRepositoryImpl implements StepRepository {
    private final StepJpaRepository stepJpaRepository;
    private final StepMapper stepMapper;
    private final StrategyMapper strategyMapper;

    @Override
    public void create(Step step, String user) {
        StepJpaEntity stepJpaEntity = stepMapper.toStepJpaEntity(step);
        stepJpaRepository.save(stepJpaEntity);
    }

    @Override
    public List<Step> findByStrategy(Strategy strategy, String user) {
        StrategyJpaEntity strategyJpaEntity = strategyMapper.toStrategyJpaEntity(strategy);
        List<StepJpaEntity> findStepJpaEntities = stepJpaRepository.findByStrategy(strategyJpaEntity);

        return findStepJpaEntities.stream()
                .map(stepMapper::toStep)
                .toList();
    }

    @Override
    public void update(Step step, String user) {
        StepJpaEntity stepJpaEntity = stepMapper.toStepJpaEntity(step);
        stepJpaRepository.save(stepJpaEntity);
    }

    @Override
    public void delete(Step step, String user) {
        StepJpaEntity stepJpaEntity = stepMapper.toStepJpaEntity(step);
        stepJpaRepository.delete(stepJpaEntity);
    }
}
