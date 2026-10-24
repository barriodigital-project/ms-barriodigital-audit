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
- Lombok
- Spring Boot Actuator
- Docker

## Puerto

```text
8086
```

## Base de datos

```text
audit_db
```

Al ejecutar desde esta carpeta, Spring carga `DB_PASSWORD` desde el archivo
compartido `../.env` del workspace. Ese archivo local no se versiona.

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

```bash
curl http://localhost:8086/api/v1/audit/requests/00000000-0000-0000-0000-000000000001/timeline
```

```json
[
  {
    "id": "6dcc94c3-d355-4ff4-9cb2-52e15952ffdc",
    "requestId": "00000000-0000-0000-0000-000000000001",
    "eventType": "REQUEST_CREATED",
    "actorId": "citizen-123",
    "message": "Solicitud ingresada",
    "timestamp": "2026-01-01T10:00:00Z"
  }
]
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

El BFF/gateway autentica al usuario y valida permisos (`ADMIN`/`AUDITOR`).
Este servicio no valida tokens y debe aceptar solo tráfico interno; no exponer
directamente el puerto `8086`.

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

Se implementan el modelo, persistencia y endpoint de consulta del timeline. Sin
una fuente de escritura, el timeline no tiene eventos: el endpoint responde
HTTP 404 hasta integrar Kafka u otro mecanismo de carga. La ingesta no forma
parte de esta etapa.

## Configuración local

El servicio escucha en `localhost:8086` y usa MySQL en `localhost:3306` por
defecto. Se pueden sobrescribir `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`
y `DB_PASSWORD`. `JPA_DDL_AUTO` controla el DDL de Hibernate (por defecto
`update`; usar migraciones controladas en producción). Actuator expone
`health`, `info` y `metrics` bajo `/actuator`.

## Service Discovery

El servicio se registra en Eureka. Por defecto usa
`http://localhost:8761/eureka/`; se puede cambiar mediante `EUREKA_SERVER_URL`.

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
