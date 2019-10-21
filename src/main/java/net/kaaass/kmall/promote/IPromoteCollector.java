package net.kaaass.kmall.promote;

/**
 * 将打折上下文转为结果
 * @param <S> 打折上下文类型
 */
public interface IPromoteCollector<S extends OrderPromoteContext> {

    OrderPromoteResult collect(S context);
}
