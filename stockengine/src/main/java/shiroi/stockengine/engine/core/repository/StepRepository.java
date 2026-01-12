package shiroi.stockengine.engine.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shiroi.stockengine.engine.core.domain.Step;
import shiroi.stockengine.engine.stockstrategy.model.StockStrategyType;

public interface StepRepository extends JpaRepository<Step, Long> {
    void createStep(StockStrategyType strategy, String user);
    List<Step> findByStrategyAndUser(StockStrategyType strategy, String user);
    void updateStep(StockStrategyType strategy, String user);
    void deleteByStrategyAndUser(StockStrategyType strategy, String user);
}
