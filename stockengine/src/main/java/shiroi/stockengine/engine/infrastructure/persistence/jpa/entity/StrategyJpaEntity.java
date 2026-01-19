package shiroi.stockengine.engine.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StrategyJpaEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String strategyName;

    @OneToMany(mappedBy = "strategyJpaEntity")
    List<StepJpaEntity> stepJpaEntities;

    @OneToMany(mappedBy = "strategyJpaEntity")
    List<StepTransitionJpaEntity> stepTransitionJpaEntities;

    @Builder
    public StrategyJpaEntity(String strategyName, List<StepJpaEntity> stepJpaEntities,
                             List<StepTransitionJpaEntity> stepTransitionJpaEntities) {
        this.strategyName = strategyName;
        this.stepJpaEntities = stepJpaEntities;
        this.stepTransitionJpaEntities = stepTransitionJpaEntities;
    }
}
