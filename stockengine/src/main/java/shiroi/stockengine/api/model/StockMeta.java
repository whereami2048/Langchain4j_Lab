package shiroi.stockengine.api.model;

import java.math.BigDecimal;

public record StockMeta(
    String ticker,
    String companyName,
    String sector,
    String market,
    Double marketCap
) {
}
