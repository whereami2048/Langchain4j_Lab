package shiroi.stockengine.engine;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineEventResult;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import shiroi.stockengine.engine.assistants.AssistantManager;
import shiroi.stockengine.engine.stockstrategy.StockStrategyManager;
import shiroi.stockengine.engine.stockstrategy.model.StockStrategyType;
import shiroi.stockengine.engine.stockstrategy.strategies.common.StockStrategy;
import shiroi.stockengine.engine.stockstrategy.strategies.longtermstrategy.model.LongTermStrategyEvent;
import shiroi.stockengine.engine.stockstrategy.strategies.common.StrategyEvent;
import shiroi.stockengine.engine.stockstrategy.strategies.common.StrategyState;
import shiroi.stockengine.engine.util.ResourceFileReader;

@Component
@RequiredArgsConstructor
public class StockEngine {

    private final AssistantManager assistantManager;
    private final StockStrategyManager strategyManager;
    private final ResourceFileReader resourceFileReader;

    public List<String> analyzeStockByStrategy(StockStrategyType strategyType) {
        //API 계층에서 받은 전략 구분자로 전략 선택
        StockStrategy<StrategyState, StrategyEvent> strategy = strategyManager.choice(strategyType);

        //선택한 전략에 따라 apply 메소드에 필요한 인자로 커스텀하는 과정... -> 공통 인자 추출 및 전략별 커스텀 인자 넘기는 포맷 결정 필요
        StateMachine<StrategyState, StrategyEvent> graph = getStateMachine(strategy);

        //State Machine 시작
        graph.startReactively().block();

        //최초 State 실행
        Message<StrategyEvent> trigger = MessageBuilder.<StrategyEvent>withPayload(LongTermStrategyEvent.START).build();
        StateMachineEventResult<StrategyState, StrategyEvent> result = graph.sendEvent(Mono.just(trigger)).blockFirst();

        //전략에 입각한 분석 결과를 aggregate하는 과정... -> aggregator 로직 필요

        //결과 반환... -> 응답 포맷 결정 필요
        return Collections.emptyList();
    }

    private StateMachine<StrategyState, StrategyEvent> getStateMachine(StockStrategy<StrategyState, StrategyEvent> strategy) {
        StateMachine<StrategyState, StrategyEvent> graph = strategy.getFlow();
        graph.addStateListener(new StateMachineListenerAdapter<>() {
            @Override
            public void stateChanged(State<StrategyState, StrategyEvent> from,
                                     State<StrategyState, StrategyEvent> to) {
                System.out.println("STATE CHANGED: " +
                        (from == null ? "null" : from.getId()) + " -> " + to.getId());
            }
        });
        return graph;
    }
}
