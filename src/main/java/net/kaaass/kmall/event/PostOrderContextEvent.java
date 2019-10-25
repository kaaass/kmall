package net.kaaass.kmall.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.kaaass.kmall.eventhandle.Event;
import net.kaaass.kmall.service.OrderRequestContext;

/**
 * 订单信息投送队列前事件
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostOrderContextEvent extends Event {

    private String uid;

    private OrderRequestContext context;
}
