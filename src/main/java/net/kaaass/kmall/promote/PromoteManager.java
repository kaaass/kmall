package net.kaaass.kmall.promote;

import lombok.extern.slf4j.Slf4j;
import net.kaaass.kmall.exception.BadRequestException;
import net.kaaass.kmall.exception.NotFoundException;
import net.kaaass.kmall.promote.collector.CommonCollector;
import net.kaaass.kmall.promote.collector.ViewCollector;
import net.kaaass.kmall.promote.strategy.CommonDiscountStrategy;
import net.kaaass.kmall.promote.strategy.CommonMailFreeStrategy;
import net.kaaass.kmall.promote.strategy.CommonPriceStrategy;
import net.kaaass.kmall.service.metadata.MetadataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PromoteManager {

    @Autowired
    private MetadataManager metadataManager;

    @Autowired
    private DbmsPromoteStrategyManager dbmsPromoteStrategyManager;

    @Autowired
    private ServiceAdapter serviceAdapter;

    private PromoteFlow<OrderPromoteContext> packWithDbms(PromoteFlow<OrderPromoteContext> flow) throws NotFoundException, BadRequestException {
        var strategies = dbmsPromoteStrategyManager.getAll();
        log.debug("得到打折策略：{}", strategies);
        PromoteFlow genericLessFlow = flow;
        for (var strategy : strategies) {
            genericLessFlow = genericLessFlow.on(dbmsPromoteStrategyManager.createFromDbms(strategy, serviceAdapter));
        }
        return genericLessFlow;
    }

    /**
     * 从数据库构建打折流执行器
     * @return 打折流执行器
     * @param forView
     */
    private PromoteExecutor buildExecutorFromDbms(boolean forView) {
        var flow = PromoteFlow.start()
                .on(CommonPriceStrategy.INSTANCE); // 必须最前
        // 从数据库读取策略
        try {
            flow = packWithDbms(flow);
        } catch (NotFoundException | BadRequestException e) {
            log.warn("从数据库构建策略失败！", e);
        }
        // Pipeline Valve
        return flow
                .on(new CommonDiscountStrategy(metadataManager))
                .on(CommonMailFreeStrategy.INSTANCE)
                .collect(forView ? ViewCollector.INSTANCE : CommonCollector.INSTANCE);
    }

    /**
     * 对某个订单进行处理
     * @param context 订单上下文
     * @return 折扣结果
     */
    public OrderPromoteResult doOnOrder(OrderPromoteContext context) {
        var executor = buildExecutorFromDbms(context.isForView());
        return executor.execute(context);
    }
}
