package cl.brarriodigital.audit.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.brarriodigital.audit.dto.response.AuditEventResponse;
import cl.brarriodigital.audit.service.AuditService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/audit/requests")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService service;

    @GetMapping("/{requestId}/timeline")
    public List<AuditEventResponse> getTimeline(@PathVariable UUID requestId) {
        return service.findTimeline(requestId);
    }
}
