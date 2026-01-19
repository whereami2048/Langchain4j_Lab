package shiroi.stockengine.api.model.request;

public record CreateStepTransitionRequest(
        long toStepId,
        long fromStepId,
        String eventName
) {
}
