package shiroi.stockengine.engine.infrastructure.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shiroi.stockengine.engine.core.domain.Strategy;
import shiroi.stockengine.engine.core.repository.StrategyRepository;
import shiroi.stockengine.engine.infrastructure.persistence.jpa.entity.StrategyJpaEntity;
import shiroi.stockengine.engine.infrastructure.persistence.jpa.repository.StrategyJpaRepository;
import shiroi.stockengine.engine.infrastructure.persistence.mapper.StrategyMapper;

@Repository
@RequiredArgsConstructor
public class StrategyRepositoryImpl implements StrategyRepository {

    private final StrategyJpaRepository strategyJpaRepository;
    private final StrategyMapper strategyMapper;

    @Override
    public Strategy findById(Long strategyId) {
        StrategyJpaEntity strategyJpaEntity = strategyJpaRepository.findById(strategyId).orElseThrow();
        return strategyMapper.toStrategy(strategyJpaEntity);
    }

    @Override
    public Strategy findByUser(String user) {
        return strategyJpaRepository.findByUser(user);
    }
}
