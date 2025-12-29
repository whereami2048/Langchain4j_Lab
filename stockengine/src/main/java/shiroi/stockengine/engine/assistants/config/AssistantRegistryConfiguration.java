package shiroi.stockengine.engine.assistants.config;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shiroi.stockengine.engine.assistants.Assistant;
import shiroi.stockengine.engine.assistants.model.AssistantType;

@Configuration
public class AssistantRegistryConfiguration {

    @Bean
    public Map<AssistantType, Assistant> assistantRegistry(List<Assistant> assistants) {
        EnumMap<AssistantType, Assistant> assistantEnumMap = new EnumMap<>(AssistantType.class);
        assistants.forEach(assistant -> assistantEnumMap.put(assistant.getType(), assistant));
        return assistantEnumMap;
    }
}
