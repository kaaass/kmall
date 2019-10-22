package net.kaaass.kmall.promote;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kaaass.kmall.vo.PromoteStrategyInfoVo;

/**
 * 打折策略接口
 * @param <S> 打折源类型
 * @param <T> 打折对象类型
 */
public interface IPromoteStrategy<S extends OrderPromoteContext, T extends OrderPromoteContext> {

    Result<T> doPromote(S context);

    PromoteStrategyInfoVo getPromoteInfo();

    @Getter
    @AllArgsConstructor
    public static class Result<T extends OrderPromoteContext> {

        boolean ok = false;

        T context;

        public Result(boolean ok) {
            this.ok = ok;
        }
    }
}
