package net.kaaass.kmall.promote;

import java.util.ArrayList;
import java.util.List;

/**
 * 打折流
 */
public class PromoteFlow<T extends OrderPromoteContext> {

    /**
     * 打折策略
     */
    private List<IPromoteStrategy> strategies;

    PromoteFlow() {
        this.strategies = new ArrayList<>();
    }

    private PromoteFlow(List<IPromoteStrategy> strategies) {
        this.strategies = strategies;
    }

    public <S extends OrderPromoteContext> PromoteFlow<S> on(IPromoteStrategy<T, S> strategy) {
        this.strategies.add(strategy);
        return new PromoteFlow<>(this.strategies);
    }

    public PromoteExecutor collect(IPromoteCollector<T> collector) {
        // TODO 修改strategies为深复制
        return new PromoteExecutor(this.strategies, collector);
    }

    /**
     * 开启一个打折流
     * @return
     */
    public static PromoteFlow<OrderPromoteContext> start() {
        return new PromoteFlow<>();
    }
}
