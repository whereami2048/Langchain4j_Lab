package shiroi.stockengine.engine.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public record StepTransition(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "from_step_id", nullable = false)
        Step fromStep,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "to_step_id", nullable = false)
        Step toStep,

        @Column(nullable = false)
        String eventName
) {
}
