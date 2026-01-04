package shiroi.stockengine.engine.stockstrategy.strategies.longtermstrategy.guard;

import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;
import shiroi.stockengine.engine.stockstrategy.strategies.longtermstrategy.model.LongTermStrategyEvent;
import shiroi.stockengine.engine.stockstrategy.strategies.longtermstrategy.model.LongTermStrategyState;

@Component
public class LongTermStrategyGuards {
    public Guard<LongTermStrategyState, LongTermStrategyEvent> canNext() {
        return context -> true;
    }
}
