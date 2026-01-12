package shiroi.stockengine.engine;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockEngine {

    public List<String> analyzeStockByStrategy(String strategyType) {
        // 유저가 선택한 전략 조회
        // Strategy userStrategy = StrategyRepository.findByUserIdAndStrategyName(userId, strategyName)
        // StrategyResult = userStrategy.run();
        // return StrategyResult;
        return Collections.emptyList();
    }
}
