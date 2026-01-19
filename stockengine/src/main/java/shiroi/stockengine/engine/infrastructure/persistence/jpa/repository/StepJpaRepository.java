package shiroi.stockengine.engine.infrastructure.persistence.jpa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shiroi.stockengine.engine.infrastructure.persistence.jpa.entity.StepJpaEntity;
import shiroi.stockengine.engine.infrastructure.persistence.jpa.entity.StrategyJpaEntity;

public interface StepJpaRepository extends JpaRepository<StepJpaEntity, Long> {
    List<StepJpaEntity> findByStrategy(StrategyJpaEntity strategyJpaEntity);
}
