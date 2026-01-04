package shiroi.stockengine.engine.stockstrategy.strategies.longtermstrategy.nodes;

import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import shiroi.stockengine.engine.stockstrategy.strategies.common.TaskNode;
import shiroi.stockengine.engine.stockstrategy.strategies.longtermstrategy.model.LongTermStrategyEvent;
import shiroi.stockengine.engine.stockstrategy.strategies.longtermstrategy.model.LongTermStrategyState;

@Component
public class DisclosureCrawlTaskNode implements TaskNode<LongTermStrategyState, LongTermStrategyEvent> {

    @Override
    public LongTermStrategyState getState() {
        return LongTermStrategyState.DISCLOSURE_CRAWL;
    }

    @Override
    public Action<LongTermStrategyState, LongTermStrategyEvent> stateAction() {
        return context -> System.out.println("Crawl Disclosure...");
    }
}
