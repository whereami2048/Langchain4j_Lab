package shiroi.stockengine.engine.core;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import reactor.core.publisher.Mono;

public record StrategyContext(
        StateMachine<String, String> stateMachine
) {
    public void run() {
        stateMachine.startReactively().block();

        Message<String> trigger = MessageBuilder.withPayload("startStrategy").build();
        stateMachine.sendEvent(Mono.just(trigger)).blockFirst();
    }
}
