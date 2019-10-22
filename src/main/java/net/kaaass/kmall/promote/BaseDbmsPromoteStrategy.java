package net.kaaass.kmall.promote;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.kaaass.kmall.dto.PromoteStrategyDto;
import net.kaaass.kmall.exception.BadRequestException;
import net.kaaass.kmall.exception.BaseException;
import net.kaaass.kmall.vo.PromoteStrategyInfoVo;

import java.io.IOException;

/**
 * Dbms销售策略基类
 * @param <S>
 * @param <T>
 */
public abstract class BaseDbmsPromoteStrategy<S extends OrderPromoteContext, T extends OrderPromoteContext>
        implements IPromoteStrategy<S, T> {

    PromoteStrategyInfoVo promoteStrategyInfoVo;

    public BaseDbmsPromoteStrategy() {}

    protected abstract void initialize(PromoteStrategyDto promoteStrategyDto, ServiceAdapter serviceAdapter) throws BaseException;

    @Override
    public PromoteStrategyInfoVo getPromoteInfo() {
        return this.promoteStrategyInfoVo;
    }

    protected static <T> T parseJsonParam(String jsonStr, Class<T> clazz) throws BadRequestException {
        var mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonStr, clazz);
        } catch (IOException e) {
            throw new BadRequestException("策略参数解析错误", e);
        }
    }
}
