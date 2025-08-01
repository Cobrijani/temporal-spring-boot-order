package com.github.cobrijani.common;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface InventoryActivities {
    @ActivityMethod
    void updateInventory(OrderRequest request);
}
