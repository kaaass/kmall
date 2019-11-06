package net.kaaass.kmall.service;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * 订单类型
 */
public enum OrderType {
    /**
     * 已创建
     */
    CREATED(0),

    /**
     * 已付款
     */
    PAID(1),

    /**
     * 已发货
     */
    DELIVERED(2),

    /**
     * 已评价
     */
    COMMENTED(3),

    /**
     * 已退款
     */
    REFUNDED(4),

    /**
     * 已取消
     */
    CANCELED(5),

    /**
     * 创建错误
     */
    ERROR(6);

    private int order;

    OrderType(int order) {
        this.order = order;
    }

    public static Optional<OrderType> getTypeById(int id) {
        return Stream.of(OrderType.values())
                .filter(type -> type.order == id)
                .findAny();
    }

    public boolean less(OrderType type) {
        return order < type.order;
    }

    public boolean great(OrderType type) {
        return order > type.order;
    }
}
