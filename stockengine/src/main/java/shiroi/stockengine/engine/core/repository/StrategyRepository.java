package shiroi.stockengine.engine.core.repository;

import shiroi.stockengine.engine.core.domain.Strategy;

public interface StrategyRepository {
    Strategy findByUser(String user);
    Strategy findById(Long strategyId);
}
