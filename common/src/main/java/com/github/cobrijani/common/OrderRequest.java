package com.github.cobrijani.common;

public record OrderRequest(String productId,
                           int quantity,
                           String customerId) {
}
