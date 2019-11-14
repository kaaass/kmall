package net.kaaass.kmall.promote;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 打折流执行器
 */
@Slf4j
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
        var acceptType = collector.getInfoType();
        for (var strategy : strategies) {
            var result = strategy.doPromote(currentContext);
            log.debug("打折结果: {}",  result);
            if (acceptType.lessEq(result.resultType)) {
                currentContext = result.getContext();
                currentContext.getPromotes().add(strategy.getPromoteInfo()); // 添加打折信息
            }
        }
        return collector.collect(currentContext);
    }
}
