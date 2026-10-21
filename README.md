# ms-barriodigital-audit

Microservicio encargado de la auditoría y trazabilidad de los trámites de **BarrioDigital**.

## Responsabilidades

- Consultar eventos asociados a trámites.
- Persistir eventos recibidos cuando se implemente la ingesta.
- Mantener timeline de auditoría.
- Permitir consultas de trazabilidad.
- La ingesta de eventos desde Kafka se incorporará en una etapa posterior.

## Stack tecnológico

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Maven
- MySQL
- Spring Security
- Eureka Client
- Spring Boot Actuator
- Docker

## Puerto

```text
8086
```

## Base de datos

```text
barriodigital_audit_db
```

## Integración futura con Kafka

Tópico:

```text
requests.events
```

Consumer Group:

```text
barriodigital-audit-group
```

Eventos principales:

```text
REQUEST_CREATED
REQUEST_ADMITTED
REQUEST_RESOLVED
REQUEST_REJECTED
```

## Modelo

```text
AuditEvent
├── id
├── requestId
├── eventType
├── actorId
├── message
└── timestamp
```

## API

```http
GET /api/v1/audit/requests/{requestId}/timeline
```

## Flujo futuro

```text
Requests
   ↓
Kafka
   ↓
Audit
   ↓
MySQL
```

## Seguridad

Roles principales:

```text
ADMIN
AUDITOR
```

## Observabilidad

- Spring Boot Actuator.
- Logs.
- Métricas.
- Amazon CloudWatch.

## Estructura esperada

```text
src/
├── main/
│   ├── java/
│   │   └── cl/brarriodigital/audit/
│   │       ├── controller/
│   │       ├── dto/
│   │       │   ├── request/
│   │       │   └── response/
│   │       ├── exception/
│   │       ├── model/
│   │       ├── repository/
│   │       ├── service/
│   │       └── AuditMsApplication.java
│   └── resources/
│       └── application.properties
└── test/
```

## Alcance de la primera etapa

Este primer incremento establece la estructura por capas para preparar la API
de consulta. No incorpora Kafka ni una fuente de escritura de eventos; por lo
tanto, la ingesta y persistencia funcional del timeline quedan pendientes.

## Contratos

```text
barriodigital-contracts/openapi/audit.openapi.yaml
barriodigital-contracts/asyncapi/kafka.asyncapi.yaml
```

## Ejecución

```bash
./mvnw spring-boot:run
```

## Docker

```bash
docker build -t barriodigital/audit:1.0.0 .
```

## Estrategia Git

```text
main
develop
feature/*
fix/*
```

## Estado

🚧 Proyecto en etapa inicial de diseño e implementación.
