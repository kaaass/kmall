package net.kaaass.kmall.promote;

import java.util.List;

/**
 * 打折流执行器
 */
public class PromoteExecutor {

    private List<IPromoteStrategy> strategies;

    private IPromoteCollector collector;


    PromoteExecutor(List<IPromoteStrategy> strategies, IPromoteCollector collector) {
        this.strategies = strategies;
        this.collector = collector;
    }

    /**
     * 执行打折流的打折计算
     * @param context
     * @return
     */
    public OrderPromoteResult execute(OrderPromoteContext context) {
        OrderPromoteContext currentContext = context;
        for (var strategy : strategies) {
            var result = strategy.doPromote(currentContext);
            if (result.ok) {
                currentContext = result.getContext();
                currentContext.getPromotes().add(strategy.getPromoteInfo()); // 添加打折信息
            }
        }
        return collector.collect(currentContext);
    }
}
