package com.example.GradProJM.Model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    CUSTOMER_READ("customer:read"),
    CUSTOMER_UPDATE("customer:update"),
    CUSTOMER_CREATE("customer:create"),
    CUSTOMER_DELETE("customer:delete"),
    SHOP_READ("shop:read"),
    SHOP_UPDATE("shop:update"),
    SHOP_CREATE("shop:create"),
    SHOP_DELETE("shop:delete"),
    ;
    @Getter
    private final String permission;
}
