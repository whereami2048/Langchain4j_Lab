package shiroi.stockengine.engine.core.domain;

import lombok.Builder;

@Builder
public record StepTransition(
    Step fromStep,
    Step toStep,
    String eventName
) {
    public boolean canNextStep() {
        // TODO: 전이 조건 검사 로직 추가
        return true;
    }
}
