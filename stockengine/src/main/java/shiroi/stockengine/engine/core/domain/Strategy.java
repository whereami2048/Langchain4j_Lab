package shiroi.stockengine.engine.core.domain;

import java.util.List;
import lombok.Builder;

@Builder
public record Strategy(
    String strategyName,
    String user,
    List<Step> steps,
    List<StepTransition> stepTransitions
) {
}
