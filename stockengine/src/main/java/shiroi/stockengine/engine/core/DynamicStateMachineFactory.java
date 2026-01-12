package shiroi.stockengine.engine.core;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.config.configurers.StateConfigurer;
import org.springframework.stereotype.Component;
import shiroi.stockengine.engine.core.domain.Step;
import shiroi.stockengine.engine.core.domain.StepTransition;
import shiroi.stockengine.engine.assistants.AssistantManager;

@Slf4j
@Component
@RequiredArgsConstructor
public class DynamicStateMachineFactory {

    private final StateMachineBuilder.Builder<String, String> builder;
    private final AssistantManager assistantManager;

    /**
     * Step과 Transition 정보를 기반으로 동적으로 StateMachine 생성
     */
    public StateMachine<String, String> createStateMachine(List<Step> steps, List<StepTransition> transitions) throws Exception {

        // Step → State 이름 매핑
        Map<String, Step> stateStepMap = steps.stream()
                .collect(Collectors.toMap(Step::stepName, Function.identity()));

        // State → 나가는 Transition 매핑
        Map<String, List<StepTransition>> stateTransitionMap = transitions.stream()
                .collect(Collectors.groupingBy(t -> t.fromStep().stepName()));

        // StateMachine Builder 초기화
        StateMachineBuilder.Builder<String, String> builder = StateMachineBuilder.builder();

        // States 구성
        configureStates(builder, steps);

        // Transitions 구성
        configureTransitions(builder, transitions);

        // StateMachine 빌드
        StateMachine<String, String> stateMachine = builder.build();

        // AutoStepInterceptor 추가 (자동 이벤트 발행)
        stateMachine.getStateMachineAccessor().doWithAllRegions(accessor -> {
            accessor.addStateMachineInterceptor(new AutoTransitionInterceptor(stateTransitionMap, stateStepMap));
        });

        return stateMachine;
    }

    /**
     * States 구성
     */
    private void configureStates(StateMachineBuilder.Builder<String, String> builder, List<Step> steps) throws Exception {
        StateConfigurer<String, String> states = builder.configureStates().withStates();

        for (Step step : steps) {
            states = switch (step.type()) {
                case INITIAL -> states.initial(step.stepName());

                case NORMAL -> {
                    // Step 실행 Action 생성
                    Action<String, String> action = createStepAction(step);
                    yield states.state(step.stepName(), action);
                }

                case END -> states.end(step.stepName());
            };
        }
    }

    /**
     * Transitions 구성
     */
    private void configureTransitions(
            StateMachineBuilder.Builder<String, String> builder,
            List<StepTransition> transitions) throws Exception {

        StateMachineTransitionConfigurer<String, String> transitionBuilder =
                builder.configureTransitions();

        for (StepTransition transition : transitions) {
            transitionBuilder
                    .withExternal()
                    .source(transition.fromStep().stepName())
                    .target(transition.toStep().stepName())
                    .event(transition.eventName())
                    .and();
        }
    }

    /**
     * Step 실행 Action 생성
     */
    private Action<String, String> createStepAction(Step step) {
        return context -> {
            try {
                log.info("Executing step: {}", step.stepName());

                // Step 실행
                List<String> result = assistantManager.apply(step.prompt(), step.assistantType());
                log.info("Step '{}' executed. Result: {}", step.stepName(), result);

                context.getStateMachine().getExtendedState().getVariables()
                        .put("lastStepResult", result);
                context.getStateMachine().getExtendedState().getVariables()
                        .put("lastStepName", step.stepName());

                log.debug("Step '{}' completed. Waiting for auto-trigger...", step.stepName());

            } catch (Exception e) {
                log.error("Error executing step '{}': {}", step.stepName(), e.getMessage(), e);
                // 에러 발생 시 StateMachine에 에러 정보 저장
                context.getStateMachine().getExtendedState().getVariables()
                        .put("error", e.getMessage());
                context.getStateMachine().getExtendedState().getVariables()
                        .put("errorStep", step.stepName());
            }
        };
    }
}
