package cl.brarriodigital.audit.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.brarriodigital.audit.dto.response.AuditEventResponse;
import cl.brarriodigital.audit.exception.ResourceNotFoundException;
import cl.brarriodigital.audit.model.AuditEvent;
import cl.brarriodigital.audit.repository.AuditEventRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuditService {

    private final AuditEventRepository repository;

    public List<AuditEventResponse> findTimeline(UUID requestId) {
        var events = repository.findAllByRequestIdOrderByTimestampAscIdAsc(requestId);
        if (events.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron eventos para el trámite: " + requestId);
        }
        return events.stream().map(this::toResponse).toList();
    }

    private AuditEventResponse toResponse(AuditEvent event) {
        return new AuditEventResponse(
            event.getId(),
            event.getRequestId(),
            event.getEventType(),
            event.getActorId(),
            event.getMessage(),
            event.getTimestamp()
        );
    }
}
