package shiroi.stockengine.api.model.request;

import java.time.LocalDate;
import shiroi.stockengine.api.model.StockMeta;
import shiroi.stockengine.api.model.enums.Currency;
import shiroi.stockengine.api.model.factor.CoreFactor;

public record StockRequest(
        String name,
        LocalDate analysisDate,
        Currency baseCurrency,
        StockMeta stockMeta,
        CoreFactor coreFactor
) {
}
