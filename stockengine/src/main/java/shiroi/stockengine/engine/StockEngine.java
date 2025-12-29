package shiroi.stockengine.engine;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shiroi.stockengine.engine.assistants.AssistantManager;
import shiroi.stockengine.engine.stockstrategy.StockStrategyManager;
import shiroi.stockengine.engine.stockstrategy.model.StockStrategyType;
import shiroi.stockengine.engine.stockstrategy.strategies.StockStrategy;
import shiroi.stockengine.engine.util.ResourceFileReader;

@Component
@RequiredArgsConstructor
public class StockEngine {

    private final AssistantManager assistantManager;
    private final StockStrategyManager strategyManager;
    private final ResourceFileReader resourceFileReader;

    public List<String> analyzeStockByStrategy(StockStrategyType strategyType) {
        //API 계층에서 받은 전략 구분자로 전략 선택
        StockStrategy strategy = strategyManager.choice(strategyType);

        //선택한 전략에 따라 apply 메소드에 필요한 인자로 커스텀하는 과정... -> 공통 인자 추출 및 전략별 커스텀 인자 넘기는 포맷 결정 필요
        String prompt = resourceFileReader.readAsString(strategy.getPromptPath());
        List<String> analyzeResultsByAssistants = assistantManager.apply(prompt);

        //전략에 입각한 분석 결과를 aggregate하는 과정... -> aggregator 로직 필요

        //결과 반환... -> 응답 포맷 결정 필요
        return analyzeResultsByAssistants;
    }
}
