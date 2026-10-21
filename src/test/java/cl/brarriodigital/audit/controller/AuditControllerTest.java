package cl.brarriodigital.audit.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import cl.brarriodigital.audit.model.AuditEvent;
import cl.brarriodigital.audit.repository.AuditEventRepository;

@SpringBootTest
@AutoConfigureMockMvc
class AuditControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuditEventRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void returnsTimelineInChronologicalOrder() throws Exception {
        var requestId = UUID.randomUUID();
        saveEvent(requestId, Instant.parse("2026-01-02T10:00:00Z"), "REQUEST_ADMITTED");
        saveEvent(requestId, Instant.parse("2026-01-01T10:00:00Z"), "REQUEST_CREATED");
        saveEvent(UUID.randomUUID(), Instant.parse("2026-01-01T10:00:00Z"), "OTHER_REQUEST");

        mockMvc.perform(get("/api/v1/audit/requests/{requestId}/timeline", requestId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].eventType").value("REQUEST_CREATED"))
            .andExpect(jsonPath("$[1].eventType").value("REQUEST_ADMITTED"));
    }

    @Test
    void returnsNotFoundWhenTimelineIsEmpty() throws Exception {
        mockMvc.perform(get(
                "/api/v1/audit/requests/{requestId}/timeline",
                UUID.randomUUID()
            ))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(404));
    }

    private void saveEvent(UUID requestId, Instant timestamp, String eventType) {
        var event = new AuditEvent();
        event.setRequestId(requestId);
        event.setTimestamp(timestamp);
        event.setEventType(eventType);
        event.setActorId("operator-1");
        repository.save(event);
    }
}
