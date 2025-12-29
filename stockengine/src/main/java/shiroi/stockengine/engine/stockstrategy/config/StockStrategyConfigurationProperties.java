package shiroi.stockengine.engine.stockstrategy.config;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import shiroi.stockengine.engine.stockstrategy.model.StrategyProperty;

@ConfigurationProperties(prefix = "shiroi.stock-strategy")
public record StockStrategyConfigurationProperties(
        Map<String, StrategyProperty> properties
) {
}
