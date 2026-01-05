package shiroi.stockengine.engine.stockstrategy.strategies.common;

import dev.langchain4j.model.input.Prompt;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shiroi.stockengine.engine.assistants.Assistant;

@RequiredArgsConstructor
@Getter
public abstract class AbstractTaskNode {
    private final Prompt prompt;
    private final Assistant assistant;
}
