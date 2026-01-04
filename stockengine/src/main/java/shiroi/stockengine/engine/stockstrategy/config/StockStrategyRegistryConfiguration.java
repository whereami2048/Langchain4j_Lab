package shiroi.stockengine.engine.stockstrategy.config;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shiroi.stockengine.engine.stockstrategy.model.StockStrategyType;
import shiroi.stockengine.engine.stockstrategy.strategies.common.StockStrategyBase;

@Configuration
public class StockStrategyRegistryConfiguration {

    @Bean
    public Map<StockStrategyType, StockStrategyBase> stockStrategyRegistry(List<StockStrategyBase> stockStrategies) {
        EnumMap<StockStrategyType, StockStrategyBase> strategyEnumMap = new EnumMap<>(StockStrategyType.class);
        stockStrategies.forEach(strategy -> strategyEnumMap.put(strategy.getStrategyType(), strategy));
        return strategyEnumMap;
    }

}
