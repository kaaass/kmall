package net.kaaass.kmall.promote.strategy;

import lombok.AllArgsConstructor;
import net.kaaass.kmall.promote.IPromoteStrategy;
import net.kaaass.kmall.promote.OrderPromoteContext;
import net.kaaass.kmall.service.metadata.MetadataManager;
import net.kaaass.kmall.util.Constants;
import net.kaaass.kmall.util.NumericUtils;
import net.kaaass.kmall.vo.PromoteStrategyInfoVo;
import net.kaaass.kmall.vo.PromoteStyle;

/**
 * 商品正常打折策略
 *
 * 将会从商品元数据读入打折信息，若互斥，需要指定DisableExtra TODO
 */
@AllArgsConstructor
public class CommonDiscountStrategy implements IPromoteStrategy<OrderPromoteContext, OrderPromoteContext> {

    private MetadataManager metadataManager;

    @Override
    public Result<OrderPromoteContext> doPromote(OrderPromoteContext context) {
        float allPrice = 0;
        var products = context.getProducts();
        float price = 0;
        for (var item : products) {
            var discount = metadataManager.getForProduct(item.getProduct().getId(), Constants.KEY_DISCOUNT, "");
            price = item.getPrice();
            if (discount.length() > 0) {
                price = NumericUtils.moneyRound(price * Float.parseFloat(discount));
                item.setPrice(price);
            }
            allPrice += price;
        }
        if (allPrice == context.getPrice()) {
            return new Result<>(false); // 没打折
        } else {
            context.setPrice(allPrice);
            return new Result<>(true, context); // 打折了
        }
    }

    @Override
    public PromoteStrategyInfoVo getPromoteInfo() {
        return new PromoteStrategyInfoVo(null,
                "商品折扣",
                "该商品正在打折促销",
                PromoteStyle.GREAT);
    }
}
