package net.kaaass.kmall.promote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PromoteManager {

    public PromoteExecutor buildExecutorFromDbms() {
        return null;
    }

    public OrderPromoteResult doOnOrder(OrderPromoteContext context) {
        var executor = buildExecutorFromDbms();
        return executor.execute(context);
    }
}
