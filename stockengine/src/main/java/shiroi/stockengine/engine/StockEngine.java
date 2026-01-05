package shiroi.stockengine.engine;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shiroi.stockengine.engine.assistants.AssistantManager;
import shiroi.stockengine.engine.stockstrategy.StockStrategyManager;
import shiroi.stockengine.engine.stockstrategy.model.StockStrategyType;
import shiroi.stockengine.engine.stockstrategy.strategies.common.StockStrategy;
import shiroi.stockengine.engine.stockstrategy.strategies.common.TaskNode;

@Component
@RequiredArgsConstructor
public class StockEngine {

    private final AssistantManager assistantManager;
    private final StockStrategyManager strategyManager;
    private final TaskNodeExecutor taskNodeExecutor;

    public List<String> analyzeStockByStrategy(StockStrategyType strategyType) {
        //API 계층에서 받은 전략 구분자로 전략 선택
        StockStrategy strategy = strategyManager.choice(strategyType);

        List<TaskNode> taskFlow = strategy.taskNodes();

        taskFlow.forEach(TaskNode::execute);

        return Collections.emptyList();
    }
}
