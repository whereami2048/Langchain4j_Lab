package shiroi.stockengine.api.model.factor;

public record ValueFactor(
    Double per, // 주가수익비율 (Price / Earnings)
    Double pbr, // 주가순자산비
    Double psr
) {
}
