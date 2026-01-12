package shiroi.stockengine.engine.core;

import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptor;
import org.springframework.statemachine.transition.Transition;
import reactor.core.publisher.Mono;
import shiroi.stockengine.engine.core.domain.Step;
import shiroi.stockengine.engine.core.domain.StepTransition;
import shiroi.stockengine.engine.core.domain.StepType;

@Slf4j
public record AutoTransitionInterceptor(
        Map<String, List<StepTransition>> stateTransitionMap,
        Map<String, Step> stateStepMap
) implements StateMachineInterceptor<String, String> {

    @Override
    public void preStateChange(State<String, String> state, Message<String> message,
                               Transition<String, String> transition, StateMachine<String, String> stateMachine,
                               StateMachine<String, String> rootStateMachine) {
        String currentState = state.getId();

        // END state에 도달하면 종료
        Step currentStep = stateStepMap.get(currentState);
        if (currentStep != null && currentStep.type() == StepType.END) {
            log.info("Reached END state. Strategy execution completed.");
            return;
        }

        // 현재 State에서 나가는 Transition 찾기
        List<StepTransition> outgoingTransitions = stateTransitionMap.get(currentState);

        if (outgoingTransitions == null || outgoingTransitions.isEmpty()) {
            log.warn("No outgoing transitions found for state: {}", currentState);
            return;
        }

        // 단일 Transition인 경우 자동으로 다음 이벤트 발행
        if (outgoingTransitions.size() == 1) {
            StepTransition nextTransition = outgoingTransitions.getFirst();
            String nextEvent = nextTransition.eventName();

            log.info("Auto-triggering next event: {} -> {}", nextEvent, nextTransition.toStep().stepName());

            Message<String> trigger = MessageBuilder
                    .withPayload(nextEvent)
                    .build();

            // 비동기로 이벤트 발행 (무한 루프 방지)
            stateMachine.sendEvent(Mono.just(trigger))
                    .subscribe(
                            _ -> log.debug("Event sent successfully: {}", nextEvent),
                            error -> log.error("Error sending event: {}", error.getMessage())
                    );
        }
    }

    @Override
    public Message<String> preEvent(Message<String> message, StateMachine<String, String> stateMachine) {
        return message;
    }

    @Override
    public void postStateChange(State<String, String> state, Message<String> message,
                                Transition<String, String> transition, StateMachine<String, String> stateMachine,
                                StateMachine<String, String> rootStateMachine) {

    }

    @Override
    public StateContext<String, String> preTransition(StateContext<String, String> stateContext) {
        return null;
    }

    @Override
    public StateContext<String, String> postTransition(StateContext<String, String> stateContext) {
        return null;
    }

    @Override
    public Exception stateMachineError(StateMachine<String, String> stateMachine, Exception exception) {
        return null;
    }
}
