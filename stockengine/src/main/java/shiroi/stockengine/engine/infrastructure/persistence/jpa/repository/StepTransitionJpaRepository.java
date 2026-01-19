package shiroi.stockengine.engine.infrastructure.persistence.jpa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shiroi.stockengine.engine.infrastructure.persistence.jpa.entity.StepTransitionJpaEntity;
import shiroi.stockengine.engine.infrastructure.persistence.jpa.entity.StrategyJpaEntity;

public interface StepTransitionJpaRepository extends JpaRepository<StepTransitionJpaEntity, Long> {
    List<StepTransitionJpaEntity> findByStrategy(StrategyJpaEntity strategy);
}
