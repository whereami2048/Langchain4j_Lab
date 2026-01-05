package shiroi.stockengine.engine.stockstrategy;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shiroi.stockengine.engine.stockstrategy.model.StockStrategyType;
import shiroi.stockengine.engine.stockstrategy.strategies.common.StockStrategy;

@Component
@RequiredArgsConstructor
public class StockStrategyManager {
    private final Map<StockStrategyType, StockStrategy> strategyRegistry;

    public StockStrategy choice(StockStrategyType strategyType) {
        return strategyRegistry.get(strategyType);
    }
}
