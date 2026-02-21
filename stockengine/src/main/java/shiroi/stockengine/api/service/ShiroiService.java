package shiroi.stockengine.api.service;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shiroi.stockengine.api.model.request.CreateStepRequest;
import shiroi.stockengine.api.model.request.CreateStepTransitionRequest;
import shiroi.stockengine.engine.StockEngine;

@Service
@RequiredArgsConstructor
public class ShiroiService {
    private final StockEngine stockEngine;

    public List<String> analyzeStockByLongTermStrategy(long strategyId) {
//        try {
//            return stockEngine.analyzeStockByStrategy(strategyId);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

        return Collections.emptyList();
    }

    public void createStep(CreateStepRequest request) {
//        Step newStep = Step.builder()
//                .stepName(request.stepName())
//                .stepType(request.stepType())
//                .assistantType(request.assistantType())
//                .prompt(request.prompt())
//                .build();
//
//        stepRepository.create(newStep, "userId");
    }

    public void createStepTransition(CreateStepTransitionRequest request) {
//        List<Step> findSteps = stepRepository.findByIds(request.fromStepId(), request.toStepId());
//        StepTransition newStepTransition = StepTransition.builder()
//                .fromStep(findSteps.getFirst())
//                .toStep(findSteps.getLast())
//                .build();
//
//        stepTransitionRepository.create(newStepTransition);
    }
}
