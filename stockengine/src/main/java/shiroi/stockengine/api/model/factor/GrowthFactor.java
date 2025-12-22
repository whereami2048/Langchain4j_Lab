package shiroi.stockengine.api.model.factor;

public record GrowthFactor(
    Double revenueGrowth1y, // 최근 1년 매출 성장률
    Double epsGrowth1y // 최근 1년 EPS 성장률
) {
}
