package com.github.cobrijani.orderservice;

import com.github.cobrijani.common.OrderRequest;
import com.github.cobrijani.common.OrderWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final WorkflowClient workflowClient;

    public OrderController(WorkflowClient workflowClient) {
        this.workflowClient = workflowClient;
    }


    @PostMapping
    public ResponseEntity<Void> placeAnOrder() {
        OrderWorkflow workflow = workflowClient.newWorkflowStub(OrderWorkflow.class,
                WorkflowOptions.newBuilder()
                        .setTaskQueue("ORDER_TASK_QUEUE")
                        .setWorkflowId("workflow-" + UUID.randomUUID())
                        .build());
        OrderRequest request = new OrderRequest("product-" + UUID.randomUUID(), 2, "cust-789");
        WorkflowClient.start(workflow::processOrder, request);
        return ResponseEntity.ok().build();
    }
}
