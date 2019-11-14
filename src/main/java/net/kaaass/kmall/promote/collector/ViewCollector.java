package net.kaaass.kmall.promote.collector;

import net.kaaass.kmall.mapper.PromoteMapper;
import net.kaaass.kmall.promote.IPromoteCollector;
import net.kaaass.kmall.promote.IPromoteStrategy;
import net.kaaass.kmall.promote.OrderPromoteContext;
import net.kaaass.kmall.promote.OrderPromoteResult;

import java.util.stream.Collectors;

/**
 * 显示打折用收集器
 */
public class ViewCollector implements IPromoteCollector<OrderPromoteContext> {

    public static ViewCollector INSTANCE = new ViewCollector();

    @Override
    public IPromoteStrategy.ResultType getInfoType() {
        return IPromoteStrategy.ResultType.NOT_COND;
    }

    @Override
    public OrderPromoteResult collect(OrderPromoteContext context) {
        var result = new OrderPromoteResult();
        result.setPrice(context.getPrice()); // 仅仅是商品价格
        result.setMailPrice(context.getMailPrice());
        result.setPromotes(context.getPromotes());
        // 商品映射
        result.setProducts(context.getProducts()
                .stream()
                .map(PromoteMapper.INSTANCE::promoteItemToOrderItemDto)
                .collect(Collectors.toList()));
        return result;
    }
}
