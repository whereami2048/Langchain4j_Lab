package shiroi.stockengine.engine.stockstrategy.strategies.shorttermstrategy.config;

import dev.langchain4j.model.input.Prompt;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shiroi.stockengine.engine.assistants.AssistantManager;
import shiroi.stockengine.engine.assistants.model.AssistantType;
import shiroi.stockengine.engine.stockstrategy.model.StockStrategyType;
import shiroi.stockengine.engine.stockstrategy.strategies.shorttermstrategy.ShortTermStockStrategy;
import shiroi.stockengine.engine.stockstrategy.strategies.shorttermstrategy.nodes.ShortTermDisclosureCrawlTaskNode;
import shiroi.stockengine.engine.stockstrategy.strategies.shorttermstrategy.nodes.ShortTermNewsCrawlTaskNode;

@Configuration
@RequiredArgsConstructor
public class ShortTermStrategyFlowConfiguration {

    private final AssistantManager assistantManager;

    @Bean
    public ShortTermStockStrategy shortTermStockStrategy() {
        return new ShortTermStockStrategy(
                StockStrategyType.SHORT_TERM_STRATEGY,
                List.of(
                        new ShortTermNewsCrawlTaskNode(
                                new Prompt("ShortTerm NewsCrawl"),
                                assistantManager.getAssistant(AssistantType.GEMINI)
                        ),
                        new ShortTermDisclosureCrawlTaskNode(
                                new Prompt("ShortTerm Disclosure Crawl"),
                                assistantManager.getAssistant(AssistantType.GEMINI)
                        )
                )
        );
    }
}
