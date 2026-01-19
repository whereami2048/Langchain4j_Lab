package shiroi.stockengine.engine.core.repository;

import java.util.List;
import shiroi.stockengine.engine.core.domain.Step;
import shiroi.stockengine.engine.core.domain.Strategy;

public interface StepRepository {
    void create(Step step, String user);
    List<Step> findByStrategy(Strategy strategy, String user);
    void update(Step step, String user);
    void delete(Step Step, String user);

    Step findById(long stepId);
    List<Step> findByIds(long... stepId);

}
