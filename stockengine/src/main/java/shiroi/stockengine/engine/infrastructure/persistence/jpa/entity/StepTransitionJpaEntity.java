package shiroi.stockengine.engine.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shiroi.stockengine.engine.core.domain.Step;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class StepTransitionJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn
    private Step fromStepJpaEntity;

    @JoinColumn
    private Step toStepJpaEntity;

    @Column
    private String eventName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    StrategyJpaEntity strategyJpaEntity;

    @Builder
    public StepTransitionJpaEntity(Step fromStepJpaEntity, Step toStepJpaEntity, String eventName) {
        this.fromStepJpaEntity = fromStepJpaEntity;
        this.toStepJpaEntity = toStepJpaEntity;
        this.eventName = eventName;
    }
}


