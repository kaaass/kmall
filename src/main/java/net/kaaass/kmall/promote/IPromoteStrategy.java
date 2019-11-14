package net.kaaass.kmall.promote;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import net.kaaass.kmall.vo.PromoteStrategyInfoVo;

/**
 * 打折策略接口
 * @param <S> 打折源类型
 * @param <T> 打折对象类型
 */
public interface IPromoteStrategy<S extends OrderPromoteContext, T extends OrderPromoteContext> {

    Result<T> doPromote(S context);

    PromoteStrategyInfoVo getPromoteInfo();

    /**
     * 结果类型
     */
    enum ResultType {

        /**
         * 非打折策略
         */
        PLACEHOLDER(-1),

        /**
         * 打折失败，商品不匹配
         */
        NOT_MATCH(0),

        /**
         * 打折失败，但商品匹配
         */
        NOT_COND(1),

        /**
         * 打折成功
         */
        OK(5);

        private int order;

        ResultType(int i) {
            order = i;
        }

        boolean lessEq(ResultType resultType) {
            return order <= resultType.order;
        }
    }

    /**
     * 结果对象
     * @param <T>
     */
    @Getter
    @AllArgsConstructor
    @ToString
    class Result<T extends OrderPromoteContext> {

        ResultType resultType = ResultType.NOT_MATCH;

        T context;

        public Result(ResultType resultType) {
            this.resultType = resultType;
        }
    }
}
