package shiroi.stockengine.engine.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shiroi.stockengine.engine.core.domain.StepTransition;
import shiroi.stockengine.engine.stockstrategy.model.StockStrategyType;

public interface StepTransitionRepository extends JpaRepository<StepTransition, Long> {
    void createStepTransition(StockStrategyType strategy, String user);
    List<StepTransition> findByStrategyAndUser(StockStrategyType strategy, String user);
    void updateStepTransition(StockStrategyType strategy, String user);
    void deleteByStrategyAndUser(StockStrategyType strategy, String user);
}
