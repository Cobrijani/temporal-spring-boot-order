package com.github.cobrijani.paymentservice;

import com.github.cobrijani.common.OrderRequest;
import com.github.cobrijani.common.PaymentActivities;
import io.temporal.spring.boot.ActivityImpl;
import org.springframework.stereotype.Component;

@Component
@ActivityImpl(taskQueues = "ORDER_TASK_QUEUE")
public class PaymentActivitiesImpl implements PaymentActivities {
    @Override
    public void processPayment(OrderRequest request) {
        System.out.printf("Payment processed for customer %s, product %s\n",
                request.customerId(), request.productId());
    }
}
