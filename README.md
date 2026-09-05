# ms-barriodigital-audit

Microservicio encargado de la auditoría y trazabilidad de los trámites de **BarrioDigital**.

## Responsabilidades

- Consumir eventos desde Kafka.
- Registrar eventos asociados a trámites.
- Mantener timeline de auditoría.
- Permitir consultas de trazabilidad.
- Mantener información de solo lectura desde la API.

## Stack tecnológico

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Maven
- MySQL
- Kafka
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

## Kafka

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

## Flujo

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
│   │   └── cl/duoc/barriodigital/audit/
│   │       ├── config/
│   │       ├── consumer/
│   │       ├── controller/
│   │       ├── dto/
│   │       ├── entity/
│   │       ├── repository/
│   │       ├── service/
│   │       ├── security/
│   │       └── exception/
│   └── resources/
│       └── application.yml
└── test/
```

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
