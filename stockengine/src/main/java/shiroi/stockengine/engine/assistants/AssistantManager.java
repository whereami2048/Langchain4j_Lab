package shiroi.stockengine.engine.assistants;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shiroi.stockengine.engine.assistants.model.AssistantType;

@Component
@RequiredArgsConstructor
public class AssistantManager {
    private final Map<AssistantType, Assistant> assistantRegistry;

    public List<String> apply(String message) {
        return assistantRegistry.values()
                .stream()
                .parallel()
                .map(assistant -> assistant.execute(message))
                .toList();
    }

    public List<String> apply(String message, AssistantType... assistantTypes) {
        return Arrays.stream(assistantTypes)
                .map(assistantRegistry::get)
                .parallel()
                .map(assistant -> assistant.execute(message))
                .toList();
    }

    public List<String> apply(String message, List<AssistantType> assistantTypes) {
        return assistantTypes.stream()
                .map(assistantRegistry::get)
                .parallel()
                .map(assistant -> assistant.execute(message))
                .toList();
    }

    public Assistant getAssistant(AssistantType assistantType) {
        return assistantRegistry.get(assistantType);
    }
}
