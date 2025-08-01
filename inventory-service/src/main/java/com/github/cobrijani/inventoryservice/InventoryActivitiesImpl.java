package com.github.cobrijani.inventoryservice;

import com.github.cobrijani.common.InventoryActivities;
import com.github.cobrijani.common.OrderRequest;
import io.temporal.spring.boot.ActivityImpl;
import org.springframework.stereotype.Component;

@Component
@ActivityImpl(taskQueues = "ORDER_TASK_QUEUE")
public class InventoryActivitiesImpl implements InventoryActivities {

    @Override
    public void updateInventory(OrderRequest request) {
        System.out.printf("Inventory updated for product %s, quantity %d\n",
                request.productId(), request.quantity());
    }
}
