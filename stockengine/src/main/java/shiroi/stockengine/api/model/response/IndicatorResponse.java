package shiroi.stockengine.api.model.response;

import java.util.Map;

public record IndicatorResponse(
        String symbol,
        String timeframe,
        int barCount,
        Map<String, Object> indicators
) {}

