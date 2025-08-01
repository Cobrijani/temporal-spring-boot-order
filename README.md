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

Feel free to open issues or submit PRs to improve this example!

---

© 2025 Cobrijani  
