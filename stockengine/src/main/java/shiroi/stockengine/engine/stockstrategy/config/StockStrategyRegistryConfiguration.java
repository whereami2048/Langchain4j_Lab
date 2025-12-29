package shiroi.stockengine.engine.stockstrategy.config;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shiroi.stockengine.engine.stockstrategy.model.StockStrategyType;
import shiroi.stockengine.engine.stockstrategy.strategies.LongTermStockStrategy;
import shiroi.stockengine.engine.stockstrategy.strategies.StockStrategy;

@Configuration
public class StockStrategyRegistryConfiguration {

    @Bean
    public Map<StockStrategyType, StockStrategy> stockStrategyRegistry(List<StockStrategy> stockStrategies) {
        EnumMap<StockStrategyType, StockStrategy> strategyEnumMap = new EnumMap<>(StockStrategyType.class);
        stockStrategies.forEach(strategy -> strategyEnumMap.put(strategy.getType(), strategy));
        return strategyEnumMap;
    }

    @Bean
    public LongTermStockStrategy longTermStockStrategy() {
        return new LongTermStockStrategy(
            "classpath:/prompts/long_term_prompt.md",
                "who are you?"
        );
    }
}
