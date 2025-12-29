package shiroi.stockengine.api.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shiroi.stockengine.engine.StockEngine;
import shiroi.stockengine.engine.assistants.GeminiAssistant;
import shiroi.stockengine.engine.stockstrategy.model.StockStrategyType;

@Service
@RequiredArgsConstructor
public class ShiroiService {
    private final GeminiAssistant geminiAssistant;
    private final StockEngine stockEngine;

    public String getHelloWorld() {
        return geminiAssistant.execute("Hello World");
    }

    public List<String> analyzeStockByLongTermStrategy(StockStrategyType strategyType) {
        return stockEngine.analyzeStockByStrategy(strategyType);
    }
}
