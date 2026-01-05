package shiroi.stockengine.engine.stockstrategy.strategies.longtermstrategy.config;

import dev.langchain4j.model.input.Prompt;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shiroi.stockengine.engine.assistants.AssistantManager;
import shiroi.stockengine.engine.assistants.model.AssistantType;
import shiroi.stockengine.engine.stockstrategy.model.StockStrategyType;
import shiroi.stockengine.engine.stockstrategy.strategies.longtermstrategy.LongTermStockStrategy;
import shiroi.stockengine.engine.stockstrategy.strategies.longtermstrategy.nodes.DisclosureCrawlTaskNode;
import shiroi.stockengine.engine.stockstrategy.strategies.longtermstrategy.nodes.NewsCrawlTaskNode;

@Configuration
@RequiredArgsConstructor
public class LongTermStrategyFlowConfiguration {

    private final AssistantManager assistantManager;

    @Bean
    public LongTermStockStrategy longTermStrategy() {
        return new LongTermStockStrategy(
                StockStrategyType.LONG_TERM_STRATEGY,
                List.of(
                        new DisclosureCrawlTaskNode(
                                new Prompt("DisclosureCrawl"),
                                assistantManager.getAssistant(AssistantType.GEMINI)
                        ),
                        new NewsCrawlTaskNode(
                                new Prompt("NewsCrawl"),
                                assistantManager.getAssistant(AssistantType.GEMINI)
                        )
                )
        );
    }
}
