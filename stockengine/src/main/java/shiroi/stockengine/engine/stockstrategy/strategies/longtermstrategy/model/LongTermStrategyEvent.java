package shiroi.stockengine.engine.stockstrategy.strategies.longtermstrategy.model;

import shiroi.stockengine.engine.stockstrategy.strategies.common.StrategyEvent;

public enum LongTermStrategyEvent implements StrategyEvent {
    START,
    NEWS_CRAWL,
    DISCLOSURE_CRAWL
}
