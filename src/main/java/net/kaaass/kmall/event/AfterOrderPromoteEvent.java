package net.kaaass.kmall.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.kaaass.kmall.eventhandle.Cancelable;
import net.kaaass.kmall.eventhandle.Event;
import net.kaaass.kmall.promote.OrderPromoteResult;

/**
 * 订单打折后事件
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Cancelable
public class AfterOrderPromoteEvent extends Event {

    private OrderPromoteResult promoteResult;
}
