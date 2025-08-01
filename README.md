# temporal-spring-boot-order

Microservice-based demo using **Temporal** with **Spring Boot** to handle a simple **Order** workflow—processing orders
by orchestrating **Inventory** and **Payment** services.

---

## 🚀 Features

- 3 Spring Boot microservices:
    - **order-service**: starts the workflow
    - **inventory-service**: updates inventory
    - **payment-service**: processes payment
- Spring Boot integration using the **Temporal Java SDK starter**
- Workflow coordination using a shared task queue (`ORDER_TASK_QUEUE`)
- REST API endpoint to initiate order execution
- Docker Compose setup including **PostgreSQL** and **Temporal Server** for persistence

---

## 📦 Repository Structure

```
/
|– common/
|   |– model/          # Shared model types like OrderRequest
|   |– workflow/       # Workflow & Activity interfaces
|
|– order-service/
|   |– src/…
|   |– controllers/
|   |– workflow/         # OrderWorkflowImpl (with @WorkflowImplementation)
|
|– inventory-service/
|   |– src/...           # InventoryActivitiesImpl
|
|– payment-service/
|   |– src/...           # PaymentActivitiesImpl
|
|– docker-compose.yml    # Temporal + PostgreSQL setup
```

---

## ⚙️ Setup Instructions

### 1. Start Temporal Backend

```bash
docker-compose up -d
```

This spins up:

- Temporal Server (gRPC on `7233`)
- Temporal Web UI (on `8080`)
- PostgreSQL backend for durable storage

### 2. Start Microservices

Each microservice runs independently:

```bash
cd inventory-service && mvn spring-boot:run
cd payment-service && mvn spring-boot:run
cd order-service && mvn spring-boot:run
```

### 3. Place an Order via REST

```
POST http://localhost:8081/orders
Content-Type: application/json
```

✅ Returns `200 Ok`

This triggers the `OrderWorkflow`, which orchestrates activity calls across services.

---

## 📊 Monitoring & Troubleshooting

- Open **Temporal UI** at `http://localhost:8080`
    - See workflows, tasks, history, and state transitions
- Look for **Workflow Task Handler** being checked (indicates the workflow worker is active)
- If a workflow is stuck in **“Workflow Task Scheduled”**:
    - Confirm it's using the correct **task queue**
    - Verify each service registers to that queue
    - Check startup logs for `Registered Workflow` messages
    - Confirm activity logs are printed on execution

---

## 🧠 Developer Notes

- Workflows should **not** be Spring-managed beans (due to Temporal’s internal lifecycle rules), but annotated with:
  ```java
  @Component
  @WorkflowImplementation(taskQueues = "ORDER_TASK_QUEUE")
  ```
- Activity implementations can be Spring beans (`@Component`)
- Activities should be stubbed inside workflows using:
  ```java
  Workflow.newActivityStub(...)
  ```
- All services must use `WorkflowServiceStubs.newInstance()` to connect to external Temporal server

---

## 🚧 Known Pitfalls

- **Workflow Task Handler not registered**: Workflow declared but no worker picks it—check annotations and component
  scanning.
- **Activity beans not executing**: Ensure Spring scans and auto-instantiates them.
- **Workflow starting too early**: Avoid starting via `CommandLineRunner`; use REST endpoints instead.

---

## 🧪 Example log artifacts (successful startup)

```
INFO  ... Registered workflow implementation: OrderWorkflowImpl
INFO  ... Started Worker on task queue: ORDER_TASK_QUEUE
INFO  ... Received activity update request...
```

---

## 🔗 Next Enhancements

- Add **error handling** and **retry policies**
- Implement **query** and **signal** handlers for workflows
- Consider separate **task queues** for workflow vs activities
- Add **OpenTelemetry metrics** or tracing support
- Dockerize microservices into a compose setup

---

## 📖 Resources & References

- Temporal Java SDK and Spring Boot Starter integration guide:
    - [Temporal Spring Boot Demo](https://github.com/temporalio/spring-boot-demo)
    - [Order Fulfillment Workflow](https://github.com/techdozo/order-fulfillment-workflow)
    - [Temporal + Spring Boot Community Discussion](https://community.temporal.io/t/temporal-with-springboot/2734)
    - [Spring Boot Starter Temporal](https://github.com/applicaai/spring-boot-starter-temporal)
    - [Blog: Spring Boot and Temporal](https://blog.corneliadavis.com/spring-boot-and-temporal-3840114fc341)

---

Feel free to open issues or submit PRs to improve this example!

---

© 2025 Cobrijani  
