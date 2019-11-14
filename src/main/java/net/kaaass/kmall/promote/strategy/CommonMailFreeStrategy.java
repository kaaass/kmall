package net.kaaass.kmall.promote.strategy;

import lombok.AllArgsConstructor;
import net.kaaass.kmall.promote.IPromoteStrategy;
import net.kaaass.kmall.promote.OrderPromoteContext;
import net.kaaass.kmall.vo.PromoteStrategyInfoVo;
import net.kaaass.kmall.vo.PromoteStyle;

/**
 * 商品正常包邮策略
 * <p>
 * 如果有包邮商品，则包邮，若无则取最大值
 * 可以通过extra添加规则NoCommonMailFreeStrategy来禁止 TODO
 */
@AllArgsConstructor
public class CommonMailFreeStrategy implements IPromoteStrategy<OrderPromoteContext, OrderPromoteContext> {

    public static CommonMailFreeStrategy INSTANCE = new CommonMailFreeStrategy();

    @Override
    public Result<OrderPromoteContext> doPromote(OrderPromoteContext context) {
        var products = context.getProducts();
        for (var item : products) {
            if (item.getProduct().getMailPrice() == 0F) {
                context.setMailPrice(0F);
            }
        }
        return new Result<>(context.getMailPrice() == 0F ? ResultType.OK : ResultType.NOT_MATCH, context);
    }

    @Override
    public PromoteStrategyInfoVo getPromoteInfo() {
        return new PromoteStrategyInfoVo(null,
                "商品包邮",
                "该商品享受包邮特权",
                PromoteStyle.NORMAL);
    }
}
