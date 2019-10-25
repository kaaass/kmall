package net.kaaass.kmall.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.kaaass.kmall.eventhandle.Cancelable;
import net.kaaass.kmall.eventhandle.Event;
import net.kaaass.kmall.service.OrderRequestContext;

/**
 * 获得订单信息投送队列的信息事件
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Cancelable
public class GotOrderContextEvent extends Event {
    private OrderRequestContext context;
}
