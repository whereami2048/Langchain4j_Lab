package shiroi.stockengine.engine.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shiroi.stockengine.engine.assistants.model.AssistantType;
import shiroi.stockengine.engine.core.domain.StepType;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StepJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    Long userId;

    @Column
    String stepName;

    @Column
    @Enumerated(EnumType.STRING)
    StepType stepType;

    @Column
    String prompt;

    @Column
    @Enumerated(EnumType.STRING)
    AssistantType assistantType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    StrategyJpaEntity strategyJpaEntity;

    @Builder
    public StepJpaEntity(Long userId, String stepName, StepType stepType, String prompt, AssistantType assistantType) {
        this.userId = userId;
        this.stepName = stepName;
        this.stepType = stepType;
        this.prompt = prompt;
        this.assistantType = assistantType;
    }
}
