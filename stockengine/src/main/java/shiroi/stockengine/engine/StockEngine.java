package shiroi.stockengine.engine;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import shiroi.stockengine.engine.core.DynamicStateMachineFactory;
import shiroi.stockengine.engine.core.domain.Strategy;
import shiroi.stockengine.engine.core.repository.StrategyRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockEngine {

    private final StrategyRepository strategyRepository;
    private final DynamicStateMachineFactory dynamicStateMachineFactory;

    public List<String> analyzeStockByStrategy(long strategyId) throws Exception {
        // 유저가 선택한 전략 조회
        Strategy strategy = strategyRepository.findById(strategyId);

        // Strategy에 따른 StateMachine 생성
        StateMachine<String, String> stateMachine = dynamicStateMachineFactory.createStateMachine(strategy.steps(),
                strategy.stepTransitions());

        // StateMachine 활성화 및 시작
        stateMachine.startReactively().block();

        // 결과 aggregation 및 반환
        ExtendedState extendedState = stateMachine.getExtendedState();
        String finalResult = extendedState.get("finalResult", String.class);

        return Collections.emptyList();
    }
}
