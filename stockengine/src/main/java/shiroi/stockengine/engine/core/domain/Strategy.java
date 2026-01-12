package shiroi.stockengine.engine.core.domain;

import shiroi.stockengine.engine.core.StrategyContext;

public class Strategy {
    String strategyName;
    StrategyContext strategyContext;

    public void run() {
        strategyContext.run();
    }
}
