package shiroi.stockengine.engine.stockstrategy.strategies.longtermstrategy.config;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shiroi.stockengine.engine.stockstrategy.strategies.common.TaskNode;
import shiroi.stockengine.engine.stockstrategy.strategies.longtermstrategy.model.LongTermStrategyEvent;
import shiroi.stockengine.engine.stockstrategy.strategies.longtermstrategy.model.LongTermStrategyState;

@Configuration
public class LongTermStrategyTaskNodeConfiguration {

    @Bean
    public Map<LongTermStrategyState, TaskNode<LongTermStrategyState, LongTermStrategyEvent>> longTermStrategyTaskNodes(
            List<TaskNode<LongTermStrategyState, LongTermStrategyEvent>> nodes) {
        EnumMap<LongTermStrategyState, TaskNode<LongTermStrategyState, LongTermStrategyEvent>> taskNodeMap = new EnumMap<>(LongTermStrategyState.class);
        nodes.forEach(node -> taskNodeMap.put(node.getState(), node));

        return taskNodeMap;
    }
}
