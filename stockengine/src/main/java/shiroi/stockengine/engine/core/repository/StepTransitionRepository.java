package shiroi.stockengine.engine.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shiroi.stockengine.engine.core.domain.StepTransition;

public interface StepTransitionRepository extends JpaRepository<StepTransition, Long> {
    void createStepTransition(String strategy, String user);
    List<StepTransition> findByStrategyAndUser(String strategy, String user);
    void updateStepTransition(String strategy, String user);
    void deleteByStrategyAndUser(String strategy, String user);
}
