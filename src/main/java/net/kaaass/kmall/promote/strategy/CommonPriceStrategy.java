package net.kaaass.kmall.promote.strategy;

import net.kaaass.kmall.promote.IPromoteStrategy;
import net.kaaass.kmall.promote.OrderPromoteContext;
import net.kaaass.kmall.vo.PromoteStrategyInfoVo;

/**
 * 普通价格计算
 *
 * 价格相加，邮费取最大值
 */
public class CommonPriceStrategy implements IPromoteStrategy<OrderPromoteContext, OrderPromoteContext> {

    public static CommonPriceStrategy INSTANCE = new CommonPriceStrategy();

    @Override
    public Result<OrderPromoteContext> doPromote(OrderPromoteContext context) {
        float totalPrice = 0;
        float totalMailPrice = 0;
        var products = context.getProducts();
        for (var product : products) {
            var price = product.getProduct().getPrice() * product.getCount();
            totalPrice += price;
            product.setPrice(price);
            totalMailPrice = Math.max(totalMailPrice, product.getProduct().getMailPrice());
        }
        context.setPrice(totalPrice);
        context.setMailPrice(totalMailPrice);
        return new Result<>(ResultType.PLACEHOLDER, context); // 不算做折扣
    }

    /**
     * 不应该执行到
     * @return
     */
    @Override
    public PromoteStrategyInfoVo getPromoteInfo() {
        return null;
    }
}
