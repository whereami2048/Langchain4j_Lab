package shiroi.stockengine.engine;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockEngine {

    public List<String> analyzeStockByStrategy(long strategyId) throws Exception {
//        // 유저가 선택한 전략 조회
//
//        // Strategy에 따른 StateMachine 생성
//        StateMachine<String, String> stateMachine = dynamicStateMachineFactory.createStateMachine(strategy.steps(),
//                strategy.stepTransitions());
//
//        // StateMachine 활성화 및 시작
//        stateMachine.startReactively().block();
//
//        // 결과 aggregation 및 반환
//        ExtendedState extendedState = stateMachine.getExtendedState();
//        String finalResult = extendedState.get("finalResult", String.class);
//
//        return Collections.emptyList();
        return Collections.emptyList();
    }
}
