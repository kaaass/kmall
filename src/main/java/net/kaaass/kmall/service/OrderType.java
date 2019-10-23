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
     * 已完成
     */
    FINISHED(2);

    private int order;

    OrderType(int order) {
        this.order = order;
    }

    public boolean less(OrderType type) {
        return order < type.order;
    }
}
