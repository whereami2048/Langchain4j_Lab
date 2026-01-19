package shiroi.stockengine.engine.infrastructure.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shiroi.stockengine.engine.core.domain.Strategy;
import shiroi.stockengine.engine.infrastructure.persistence.jpa.entity.StrategyJpaEntity;

public interface StrategyJpaRepository extends JpaRepository<StrategyJpaEntity, Long> {
    Strategy findByUser(String user);
}
