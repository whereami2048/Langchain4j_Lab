package shiroi.stockengine.engine.stockstrategy.strategies.common;

import lombok.Getter;
import org.springframework.statemachine.StateMachine;
import shiroi.stockengine.engine.stockstrategy.model.StockStrategyType;

@Getter
public abstract class StockStrategy<S extends StrategyState, E extends StrategyEvent> implements StockStrategyBase{
    public StockStrategyType stockStrategyType;
    StateMachine<S, E> flow;

    public StockStrategy(StateMachine<S, E> flow) {
        this.flow = flow;
    }
}
