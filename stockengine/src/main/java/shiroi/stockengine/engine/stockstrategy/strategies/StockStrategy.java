package shiroi.stockengine.engine.stockstrategy.strategies;

import shiroi.stockengine.engine.stockstrategy.model.StockStrategyType;

public abstract class StockStrategy {
    String promptPath;

    abstract public StockStrategyType getType();
    abstract public String getPromptPath();
}
