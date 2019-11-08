package net.kaaass.kmall.promote.collector;

import net.kaaass.kmall.mapper.PromoteMapper;
import net.kaaass.kmall.promote.IPromoteCollector;
import net.kaaass.kmall.promote.OrderPromoteContext;
import net.kaaass.kmall.promote.OrderPromoteResult;

import java.util.stream.Collectors;

public class CommonCollector implements IPromoteCollector<OrderPromoteContext> {

    public static CommonCollector INSTANCE = new CommonCollector();

    @Override
    public OrderPromoteResult collect(OrderPromoteContext context) {
        var result = new OrderPromoteResult();
        result.setPrice(context.getPrice() + context.getMailPrice()); // 总价应该包含邮费
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
