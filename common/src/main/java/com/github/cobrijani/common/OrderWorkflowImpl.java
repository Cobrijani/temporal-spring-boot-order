package com.github.cobrijani.common;

import io.temporal.activity.ActivityOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;

import java.time.Duration;


@WorkflowImpl(
        taskQueues = {"ORDER_TASK_QUEUE"})
public class OrderWorkflowImpl implements OrderWorkflow {
    private final InventoryActivities inventory = Workflow.newActivityStub(
            InventoryActivities.class,
            ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(10)).build()
    );

    private final PaymentActivities payment = Workflow.newActivityStub(
            PaymentActivities.class,
            ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(10)).build()
    );

    /**
     * Processes an order by updating inventory and processing payment.
     *
     * @param request the order request containing product, quantity, and customer information
     */
    @Override
    public void processOrder(OrderRequest request) {
        inventory.updateInventory(request);
        payment.processPayment(request);
    }
}
