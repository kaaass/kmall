package net.kaaass.kmall.promote.strategy.dbms;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.kaaass.kmall.dao.entity.CategoryEntity;
import net.kaaass.kmall.dto.PromoteStrategyDto;
import net.kaaass.kmall.exception.BaseException;
import net.kaaass.kmall.exception.NotFoundException;
import net.kaaass.kmall.promote.BaseDbmsPromoteStrategy;
import net.kaaass.kmall.promote.OrderPromoteContext;
import net.kaaass.kmall.promote.ServiceAdapter;
import net.kaaass.kmall.util.NumericUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 满减策略
 */
@Slf4j
public class DiscountEveryStrategy extends BaseDbmsPromoteStrategy<OrderPromoteContext, OrderPromoteContext> {

    /**
     * 参数
     */
    @Data
    private static class Param {

        /**
         * 作用对象品类
         *
         * 若为null则为通配
         */
        private String categoryId;

        /**
         * 满
         */
        private float every;

        /**
         * 减
         */
        private float discount;

        /**
         * 最大打折次数
         *
         * 若小于零则无限制
         */
        private int maxCount;
    }

    private Param param = null;

    private List<String> categories = null;

    @Override
    protected void initialize(PromoteStrategyDto promoteStrategyDto, ServiceAdapter serviceAdapter) throws BaseException {
        var paramStr = promoteStrategyDto.getParam();
        this.param = parseJsonParam(paramStr, Param.class);
        // 获取所有子分类
        if (param.categoryId != null) {
            var categoryService = serviceAdapter.getCategoryService();
            var category = categoryService.getEntityById(param.categoryId);
            this.categories = categoryService.getAllSubs(category)
                    .stream()
                    .map(CategoryEntity::getId)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Result<OrderPromoteContext> doPromote(OrderPromoteContext context) {
        float allPrice = context.getPrice();
        float filteredPrice = context.getProducts()
                        .stream()
                        .filter(promoteItem -> this.categories == null
                                || this.categories.contains(promoteItem.getProduct().getCategory().getId()))
                        .reduce(0F, (acc, el) -> acc + el.getPrice(), Float::sum);
        log.debug("筛选可打折价格 {}，品类 {}", filteredPrice, this.categories);
        // 打折次数
        int discountTimes = (int) (filteredPrice / param.every);
        if (param.maxCount >= 0)
            discountTimes = Math.min(param.maxCount, discountTimes);
        if (discountTimes <= 0)
            return new Result<>(false);
        // 计算折扣
        float discount = NumericUtils.moneyRound(discountTimes * param.discount);
        context.setPrice(allPrice - discount);
        return new Result<>(true, context);
    }
}
