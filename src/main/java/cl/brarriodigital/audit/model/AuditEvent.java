package cl.brarriodigital.audit.model;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "audit_events",
    indexes = @Index(name = "idx_audit_events_request_timestamp", columnList = "request_id, timestamp")
)
@Getter
@Setter
@NoArgsConstructor
public class AuditEvent {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name = "request_id", nullable = false)
    private UUID requestId;

    @Column(name = "event_type", nullable = false, length = 80)
    private String eventType;

    @Column(name = "actor_id", length = 120)
    private String actorId;

    @Column(length = 2000)
    private String message;

    @Column(nullable = false)
    private Instant timestamp;
}
