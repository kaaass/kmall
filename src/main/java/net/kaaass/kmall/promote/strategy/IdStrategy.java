package net.kaaass.kmall.promote.strategy;

import net.kaaass.kmall.promote.IPromoteStrategy;
import net.kaaass.kmall.promote.OrderPromoteContext;
import net.kaaass.kmall.vo.PromoteStrategyInfoVo;

/**
 * 单位策略
 */
public class IdStrategy implements IPromoteStrategy<OrderPromoteContext, OrderPromoteContext> {
    @Override
    public Result<OrderPromoteContext> doPromote(OrderPromoteContext context) {
        return new Result<>(false, context);
    }

    /**
     * 不应该被运行
     * @return
     */
    @Override
    public PromoteStrategyInfoVo getPromoteInfo() {
        return null;
    }
}
