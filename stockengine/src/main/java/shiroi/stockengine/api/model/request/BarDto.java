package shiroi.stockengine.api.model.request;

public record BarDto(
        long epochMillis,
        double open,
        double high,
        double low,
        double close,
        double volume
) {}

