package shiroi.stockengine.engine.core;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.config.configurers.StateConfigurer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import shiroi.stockengine.engine.assistants.AssistantManager;
import shiroi.stockengine.engine.core.domain.Step;
import shiroi.stockengine.engine.core.domain.StepTransition;

@Slf4j
@Component
@RequiredArgsConstructor
public class DynamicStateMachineFactory {

    private final AssistantManager assistantManager;

    /**
     * Step과 Transition 정보를 기반으로 동적으로 StateMachine 생성
     */
    public StateMachine<String, String> createStateMachine(List<Step> steps, List<StepTransition> transitions) throws Exception {

        // State → 나가는 Transition 매핑
        Map<String, List<StepTransition>> stepTransitionMap = transitions.stream()
                .collect(Collectors.groupingBy(t -> t.fromStep().stepName()));

        // StateMachine Builder 초기화
        StateMachineBuilder.Builder<String, String> builder = StateMachineBuilder.builder();

        // States 구성
        configureStates(builder, steps, stepTransitionMap);

        // Transitions 구성
        configureTransitions(builder, transitions);

        // 완성된 StateMachine 반환
        return builder.build();
    }

    /**
     * States 구성
     */
    private void configureStates(StateMachineBuilder.Builder<String, String> builder, List<Step> steps,
                                 Map<String, List<StepTransition>> stepTransitionMap
    ) throws Exception {
        StateConfigurer<String, String> states = builder.configureStates().withStates();

        for (Step step : steps) {
            states = switch (step.stepType()) {
                case INITIAL -> states.initial(step.stepName());
                case NORMAL -> states.state(step.stepName(), createAction(step, stepTransitionMap.get(step.stepName())));
                case END -> states.end(step.stepName());
            };
        }
    }

    /**
     * Transitions 구성
     */
    private void configureTransitions(StateMachineBuilder.Builder<String, String> builder,
                                      List<StepTransition> transitions) throws Exception {

        StateMachineTransitionConfigurer<String, String> transitionBuilder = builder.configureTransitions();

        for (StepTransition transition : transitions) {
            transitionBuilder
                .withExternal()
                .source(transition.fromStep().stepName())
                .target(transition.toStep().stepName())
                .event(transition.eventName())
                .and();
        }
    }

    private Action<String, String> createAction(Step currentStep, List<StepTransition> currentStepTransition) {
        return context -> {
            //assistant 실행
            List<String> stepResults = assistantManager.apply(currentStep.prompt(), currentStep.assistantType());
            context.getExtendedState().getVariables().put(currentStep.stepName() + ":result", stepResults);

            // 다음 Step trigger
            List<StepTransition> canNextStepTransition = currentStepTransition.stream()
                    .filter(StepTransition::canNextStep)
                    .toList();

            if (canNextStepTransition.isEmpty()) { // 다음 스텝이 없는 경우
                log.info("[StateMachine] Stop State Machine because next step is empty");
                context.getStateMachine().stopReactively();
            }else if (canNextStepTransition.size() == 1) { // 다음 스텝이 1개인 경우
                Message<String> trigger = MessageBuilder.withPayload(canNextStepTransition.getFirst().eventName()).build();
                context.getStateMachine().sendEvent(Mono.just(trigger));
            }else { // 다음 스텝이 2개 이상인 경우
                //TODO: 스텝 분기 플로우 구현 필요
            }
        };
    }
}
