package shiroi.stockengine.api.controller;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shiroi.stockengine.api.model.request.CreateStepRequest;
import shiroi.stockengine.api.model.request.CreateStepTransitionRequest;
import shiroi.stockengine.api.model.request.IndicatorRequest;
import shiroi.stockengine.api.model.response.IndicatorResponse;
import shiroi.stockengine.api.service.ShiroiService;
import shiroi.stockengine.api.service.IndicatorService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ShiroiV1Controller {

    private final ShiroiService shiroiService;
    private final IndicatorService indicatorService;

    @GetMapping("/analyze")
    public List<String> analyzeStockByLongTermStrategy(@RequestParam long strategyId) {
        return shiroiService.analyzeStockByLongTermStrategy(strategyId);
    }

    @PostMapping("/steps")
    public void createStep(@RequestBody CreateStepRequest request) {
        shiroiService.createStep(request);
    }

    @PostMapping("/step-transitions")
    public void createStepTransition(@RequestBody CreateStepTransitionRequest request) {
        shiroiService.createStepTransition(request);
    }

    @PostMapping("/indicators")
    public IndicatorResponse calculateIndicators(@RequestBody IndicatorRequest request) {
        Map<String, Object> indicators = indicatorService.calculate(request.timeframe(), request.bars());
        return new IndicatorResponse(request.symbol(), request.timeframe(), request.bars().size(), indicators);
    }
}
