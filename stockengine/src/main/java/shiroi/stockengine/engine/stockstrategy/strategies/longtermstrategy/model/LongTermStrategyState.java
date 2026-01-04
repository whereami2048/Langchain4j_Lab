package shiroi.stockengine.engine.stockstrategy.strategies.longtermstrategy.model;

import lombok.Getter;
import shiroi.stockengine.engine.stockstrategy.strategies.common.StrategyState;

@Getter
public enum LongTermStrategyState implements StrategyState {
    START,
    NEWS_CRAWL,
    DISCLOSURE_CRAWL
}
