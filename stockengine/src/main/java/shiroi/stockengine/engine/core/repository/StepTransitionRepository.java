package shiroi.stockengine.engine.core.repository;

import java.util.List;
import shiroi.stockengine.engine.core.domain.StepTransition;
import shiroi.stockengine.engine.core.domain.Strategy;

public interface StepTransitionRepository {
    void create(StepTransition stepTransition);
    List<StepTransition> findByStrategy(Strategy strategy);
    void update(StepTransition stepTransition);
    void delete(StepTransition stepTransition);
}
