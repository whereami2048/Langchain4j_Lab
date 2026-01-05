package shiroi.stockengine.engine.stockstrategy.strategies.common;

import java.util.List;
import shiroi.stockengine.engine.stockstrategy.model.StockStrategyType;

public interface StockStrategy {
    StockStrategyType strategyType();
    List<TaskNode> taskNodes();
}
