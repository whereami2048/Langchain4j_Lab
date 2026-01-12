package shiroi.stockengine.engine.core.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import shiroi.stockengine.engine.assistants.model.AssistantType;

public record Step(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id,

        @Column(nullable = false)
        String stepName,

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        StepType type,

        @Column(columnDefinition = "prompt")
        String prompt,

        @Enumerated(EnumType.STRING)
        AssistantType assistantType,

        @Column(nullable = false)
        String strategyType,

        @OneToMany(mappedBy = "fromStep", cascade = CascadeType.ALL, orphanRemoval = true)
        List<StepTransition> outgoingTransitions,

        @OneToMany(mappedBy = "toStep", cascade = CascadeType.ALL, orphanRemoval = true)
        List<StepTransition> incomingTransitions
) {
}
