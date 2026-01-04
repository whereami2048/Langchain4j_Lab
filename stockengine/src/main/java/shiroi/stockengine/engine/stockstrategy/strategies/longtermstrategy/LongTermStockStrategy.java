package shiroi.stockengine.engine.stockstrategy.strategies.longtermstrategy;

import lombok.Getter;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import shiroi.stockengine.engine.stockstrategy.model.StockStrategyType;
import shiroi.stockengine.engine.stockstrategy.strategies.common.StockStrategy;
import shiroi.stockengine.engine.stockstrategy.strategies.longtermstrategy.model.LongTermStrategyEvent;
import shiroi.stockengine.engine.stockstrategy.strategies.longtermstrategy.model.LongTermStrategyState;

@Component
@Getter
public class LongTermStockStrategy extends StockStrategy<LongTermStrategyState, LongTermStrategyEvent> {

    public LongTermStockStrategy(StateMachine<LongTermStrategyState, LongTermStrategyEvent> graph) {
        super(graph);
        super.stockStrategyType = StockStrategyType.LONG_TERM_STRATEGY;
    }

    @Override
    public StockStrategyType getStrategyType() {
        return this.stockStrategyType;
    }
}
