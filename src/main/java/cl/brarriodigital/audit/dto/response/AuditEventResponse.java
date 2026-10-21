package cl.brarriodigital.audit.dto.response;

import java.time.Instant;
import java.util.UUID;

public record AuditEventResponse(
    UUID id,
    UUID requestId,
    String eventType,
    String actorId,
    String message,
    Instant timestamp
) {
}
