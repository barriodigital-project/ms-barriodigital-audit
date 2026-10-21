package cl.brarriodigital.audit.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.brarriodigital.audit.model.AuditEvent;

public interface AuditEventRepository extends JpaRepository<AuditEvent, UUID> {

    List<AuditEvent> findAllByRequestIdOrderByTimestampAscIdAsc(UUID requestId);
}
