package net.kaaass.kmall.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.kaaass.kmall.eventhandle.Event;
import net.kaaass.kmall.promote.OrderPromoteContext;
import net.kaaass.kmall.promote.strategy.dbms.JavascriptStrategy;
import net.kaaass.kmall.vo.PromoteStrategyInfoVo;

/**
 * JavaScript策略执行前事件
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BeforeScriptStrategyExecuteEvent extends Event {

    private OrderPromoteContext context;

    private PromoteStrategyInfoVo strategyInfo;

    private JavascriptStrategy.Param param;

    private Object extraInfo;
}
