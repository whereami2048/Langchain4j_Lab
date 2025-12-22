package shiroi.stockengine.api.model.factor;

public record QualityFactor(
    Double roe, // 자기자본이익
    Double operatingMargin, // 영업이익률
    Double debtEquityRatio // 부채 비율 (Debt / Equity)
) {
}
