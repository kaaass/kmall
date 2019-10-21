package net.kaaass.kmall.promote;

import lombok.extern.slf4j.Slf4j;
import net.kaaass.kmall.promote.collector.CommonCollector;
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

    public PromoteExecutor buildExecutorFromDbms() {
        return PromoteFlow.start()
                .on(CommonPriceStrategy.INSTANCE) // 必须最前
                // TODO 增加数据库指定
                .on(new CommonDiscountStrategy(metadataManager))
                .on(CommonMailFreeStrategy.INSTANCE)
                .collect(CommonCollector.INSTANCE);
    }

    public OrderPromoteResult doOnOrder(OrderPromoteContext context) {
        var executor = buildExecutorFromDbms();
        return executor.execute(context);
    }
}
