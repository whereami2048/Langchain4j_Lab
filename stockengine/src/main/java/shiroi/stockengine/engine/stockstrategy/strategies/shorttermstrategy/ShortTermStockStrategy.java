package shiroi.stockengine.engine.stockstrategy.strategies.shorttermstrategy;

import java.util.List;
import shiroi.stockengine.engine.stockstrategy.model.StockStrategyType;
import shiroi.stockengine.engine.stockstrategy.strategies.common.StockStrategy;
import shiroi.stockengine.engine.stockstrategy.strategies.common.TaskNode;

public record ShortTermStockStrategy(
        StockStrategyType strategyType,
        List<TaskNode> taskNodes
) implements StockStrategy {

}
