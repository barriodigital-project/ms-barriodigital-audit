package cl.brarriodigital.audit.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import cl.brarriodigital.audit.model.AuditEvent;

@DataJpaTest
class AuditEventRepositoryTest {

    @Autowired
    private AuditEventRepository repository;

    @Test
    void findsEventsInTimelineOrderForOneRequest() {
        var requestId = UUID.randomUUID();
        saveEvent(requestId, Instant.parse("2026-01-02T00:00:00Z"), "REQUEST_ADMITTED");
        saveEvent(requestId, Instant.parse("2026-01-01T00:00:00Z"), "REQUEST_CREATED");
        saveEvent(UUID.randomUUID(), Instant.parse("2026-01-01T00:00:00Z"), "OTHER_REQUEST");

        var timeline = repository.findAllByRequestIdOrderByTimestampAscIdAsc(requestId);

        assertThat(timeline).extracting(AuditEvent::getEventType)
            .containsExactly("REQUEST_CREATED", "REQUEST_ADMITTED");
    }

    private void saveEvent(UUID requestId, Instant timestamp, String eventType) {
        var event = new AuditEvent();
        event.setRequestId(requestId);
        event.setTimestamp(timestamp);
        event.setEventType(eventType);
        repository.save(event);
    }
}
