package net.kaaass.kmall.promote;

import net.kaaass.kmall.vo.PromoteStrategyInfoVo;

/**
 * 打折策略接口
 * @param <S> 打折源类型
 * @param <T> 打折对象类型
 */
public interface IPromoteStrategy<S extends OrderPromoteContext, T extends OrderPromoteContext> {

    T doPromote(S context);

    PromoteStrategyInfoVo getPromoteInfo();
}
