package shiroi.stockengine.api.model.factor;

public record CoreFactor(
    ValueFactor valueFactor,
    QualityFactor qualityFactor,
    MomentumFactor momentumFactor,
    GrowthFactor growthFactor
) {
}
