# ms-barriodigital-audit

Microservicio encargado de la auditoría y trazabilidad de eventos de **BarrioDigital**.

## Descripción

Mantiene un timeline consultable de los acontecimientos relevantes asociados a los trámites.

La auditoría se alimenta principalmente mediante eventos de Kafka, evitando acoplarla directamente al flujo transaccional principal.

## Responsabilidades

- Consumir eventos de dominio.
- Persistir eventos relevantes.
- Mantener trazabilidad.
- Registrar actor.
- Registrar acción.
- Registrar fecha y hora.
- Registrar cambios de estado.
- Mantener `traceId` y `correlationId`.
- Permitir consultas históricas.
- Exponer información únicamente de lectura.

## Stack tecnológico

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Maven
- MySQL
- Apache Kafka
- Spring Security
- Spring Boot Actuator
- Docker

## Puerto local

```text
8086
```

## Base de datos

```text
barriodigital_audit_db
```

Este microservicio es propietario exclusivo de la base de auditoría.

## Kafka

Tópico principal consumido:

```text
requests.events
```

Eventos esperados:

```text
REQUEST_CREATED
REQUEST_ADMITTED
REQUEST_IN_PROGRESS
REQUEST_ON_SITE
REQUEST_RESOLVED
REQUEST_REJECTED
```

Puede posteriormente publicar o mantener:

```text
audit.timeline
```

## Datos de auditoría

Ejemplo conceptual:

```json
{
  "eventId": "uuid",
  "requestId": "REQ-2026-000001",
  "eventType": "REQUEST_ADMITTED",
  "actorId": "user-id",
  "previousStatus": "INGRESADO",
  "newStatus": "ADMITIDO",
  "timestamp": "2026-09-05T13:30:00Z",
  "traceId": "trace-id",
  "correlationId": "REQ-2026-000001"
}
```

## API

Solo lectura:

```http
GET /api/v1/audit/events
GET /api/v1/audit/requests/{requestId}/timeline
```

Filtros previstos:

```text
requestId
actorId
eventType
from
to
page
size
```

## Restricciones

No se expondrán operaciones públicas de:

```http
POST
PUT
PATCH
DELETE
```

sobre registros históricos de auditoría.

## Seguridad

Roles permitidos principalmente:

```text
ADMIN
AUDITOR
```

## Buenas prácticas Kafka

- Consumer Groups.
- Idempotencia.
- Reintentos.
- Dead Letter Topic.
- Manejo de offsets.
- Versionamiento de eventos.
- `traceId`.
- `correlationId`.

## Observabilidad

Métricas relevantes:

- eventos consumidos;
- eventos fallidos;
- consumer lag;
- tiempo de procesamiento;
- DLT.

Herramientas:

- Spring Boot Actuator.
- CloudWatch.

## Variables de entorno

```env
SERVER_PORT=8086

MYSQL_HOST=
MYSQL_PORT=3306
MYSQL_DATABASE=barriodigital_audit_db
MYSQL_USER=
MYSQL_PASSWORD=

KAFKA_BOOTSTRAP_SERVERS=

EUREKA_SERVER_URL=
COGNITO_ISSUER_URI=
```

## Contratos

Repositorio:

```text
barriodigital-contracts
```

Contratos:

- OpenAPI.
- AsyncAPI.

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
│   │       ├── exception/
│   │       ├── repository/
│   │       ├── security/
│   │       └── service/
│   └── resources/
│       └── application.yml
└── test/
```

## Docker

```bash
docker build -t barriodigital/audit:1.0.0 .
```

## CI/CD

GitHub Actions + SonarQube + Snyk + Docker + Amazon ECR.

## Estrategia Git

```text
main
develop
feature/*
fix/*
```

## Estado

🚧 Proyecto en etapa inicial de diseño y construcción.
