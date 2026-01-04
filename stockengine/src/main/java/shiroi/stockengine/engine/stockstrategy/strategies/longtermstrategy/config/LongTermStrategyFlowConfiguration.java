package shiroi.stockengine.engine.stockstrategy.strategies.longtermstrategy.config;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import shiroi.stockengine.engine.stockstrategy.strategies.common.TaskNode;
import shiroi.stockengine.engine.stockstrategy.strategies.longtermstrategy.guard.LongTermStrategyGuards;
import shiroi.stockengine.engine.stockstrategy.strategies.longtermstrategy.model.LongTermStrategyEvent;
import shiroi.stockengine.engine.stockstrategy.strategies.longtermstrategy.model.LongTermStrategyState;

@Configuration
@EnableStateMachine
@RequiredArgsConstructor
public class LongTermStrategyFlowConfiguration extends EnumStateMachineConfigurerAdapter<LongTermStrategyState, LongTermStrategyEvent> {

    private final LongTermStrategyGuards guards;
    private final Map<LongTermStrategyState, TaskNode<LongTermStrategyState, LongTermStrategyEvent>> nodes;

    @Override
    public void configure(StateMachineStateConfigurer<LongTermStrategyState, LongTermStrategyEvent> states) throws Exception {
        states.withStates()
            .initial(LongTermStrategyState.START)
            .state(LongTermStrategyState.NEWS_CRAWL, nodes.get(LongTermStrategyState.NEWS_CRAWL).stateAction())
            .state(LongTermStrategyState.DISCLOSURE_CRAWL, nodes.get(LongTermStrategyState.DISCLOSURE_CRAWL).stateAction())
            .end(LongTermStrategyState.DISCLOSURE_CRAWL);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<LongTermStrategyState, LongTermStrategyEvent> transitions) throws Exception {
        transitions
            .withExternal()
                .source(LongTermStrategyState.START)
                .target(LongTermStrategyState.NEWS_CRAWL)
                .event(LongTermStrategyEvent.START)
                .guard(guards.canNext())
            .and()
            .withExternal()
                .source(LongTermStrategyState.NEWS_CRAWL)
                .target(LongTermStrategyState.DISCLOSURE_CRAWL)
                .event(LongTermStrategyEvent.NEWS_CRAWL)
                .guard(guards.canNext());
    }
}
