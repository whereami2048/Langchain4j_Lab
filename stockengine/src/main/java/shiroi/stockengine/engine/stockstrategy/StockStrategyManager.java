package shiroi.stockengine.engine.stockstrategy;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shiroi.stockengine.engine.stockstrategy.model.StockStrategyType;
import shiroi.stockengine.engine.stockstrategy.strategies.common.StockStrategy;
import shiroi.stockengine.engine.stockstrategy.strategies.common.StockStrategyBase;
import shiroi.stockengine.engine.stockstrategy.strategies.common.StrategyEvent;
import shiroi.stockengine.engine.stockstrategy.strategies.common.StrategyState;

@Component
@RequiredArgsConstructor
public class StockStrategyManager {
    private final Map<StockStrategyType, StockStrategyBase> strategyRegistry;

    @SuppressWarnings("unchecked")
    public StockStrategy<StrategyState, StrategyEvent> choice(StockStrategyType strategyType) {
        return (StockStrategy<StrategyState, StrategyEvent>)strategyRegistry.get(strategyType);
    }
}
