package shiroi.stockengine.api.model.request;

import java.util.List;

public record IndicatorRequest(
    String symbol,
    String timeframe,
    List<BarDto> bars
) {

}

