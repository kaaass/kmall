package net.kaaass.kmall.service;

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
    CANCELED(5);

    private int order;

    OrderType(int order) {
        this.order = order;
    }

    public boolean less(OrderType type) {
        return order < type.order;
    }

    public boolean great(OrderType type) {
        return order > type.order;
    }
}
