package shiroi.stockengine.engine.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shiroi.stockengine.engine.core.domain.Step;

public interface StepRepository extends JpaRepository<Step, Long> {
    void createStep(String strategy, String user);
    List<Step> findByStrategyAndUser(String strategy, String user);
    void updateStep(String strategy, String user);
    void deleteByStrategyAndUser(String strategy, String user);
}
