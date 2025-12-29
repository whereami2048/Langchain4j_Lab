package shiroi.stockengine.engine.stockstrategy.strategies;

import org.springframework.stereotype.Component;
import shiroi.stockengine.engine.stockstrategy.model.StockStrategyType;

@Component
public class LongTermStockStrategy extends StockStrategy {

    private final String customProperty;

    public LongTermStockStrategy(String promptPath, String customProperty) {
        this.promptPath = promptPath;
        this.customProperty = customProperty;
    }

    @Override
    public StockStrategyType getType() {
        return StockStrategyType.LONG_TERM_STRATEGY;
    }

    @Override
    public String getPromptPath() {
        return promptPath;
    }
}
