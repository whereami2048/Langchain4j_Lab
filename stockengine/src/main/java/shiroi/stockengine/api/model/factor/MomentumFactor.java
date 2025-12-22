package shiroi.stockengine.api.model.factor;

public record MomentumFactor(
    Double return12m, // 최근 12개월간 주가 수익률
    Double return6m // 최근 12개월간 주가 수익률
) {
}
