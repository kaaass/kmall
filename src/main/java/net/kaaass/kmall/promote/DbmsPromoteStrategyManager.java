package net.kaaass.kmall.promote;

import lombok.extern.slf4j.Slf4j;
import net.kaaass.kmall.dao.repository.PromoteStrategyRepository;
import net.kaaass.kmall.dto.PromoteStrategyDto;
import net.kaaass.kmall.exception.BadRequestException;
import net.kaaass.kmall.exception.BaseException;
import net.kaaass.kmall.exception.NotFoundException;
import net.kaaass.kmall.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 管理数据库策略
 */
@Service
@Slf4j
public class DbmsPromoteStrategyManager {

    @Autowired
    private PromoteStrategyRepository promoteStrategyRepository;

    /**
     * 依照优先级获得策略顺序
     * @return 策略s
     */
    public List<PromoteStrategyDto> getAll() {
        return promoteStrategyRepository.findAllByEnabledTrueOrderByOrderAscLastUpdateTimeDesc()
                .stream()
                .map(OrderMapper.INSTANCE::promoteStrategyEntitiyToDto)
                .collect(Collectors.toList());
    }

    /**
     * 使用反射，从数据库创建策略
     * @param promoteStrategyDto 策略DTO
     * @param serviceAdapter 服务适配器
     * @return 策略
     */
    public BaseDbmsPromoteStrategy createFromDbms(PromoteStrategyDto promoteStrategyDto, ServiceAdapter serviceAdapter) throws NotFoundException, BadRequestException {
        try {
            var clazz = Class.forName(promoteStrategyDto.getClazz());
            if (!BaseDbmsPromoteStrategy.class.isAssignableFrom(clazz)) { // 需继承BaseDbmsPromoteStrategy
                throw new BadRequestException("该策略不合法！");
            }
            var constructor = clazz.getConstructor();
            BaseDbmsPromoteStrategy strategy = (BaseDbmsPromoteStrategy) constructor.newInstance();
            strategy.initialize(promoteStrategyDto, serviceAdapter);
            strategy.promoteStrategyInfoVo = OrderMapper.INSTANCE.promoteStrategyDtoToInfoVo(promoteStrategyDto);
            return strategy;
        } catch (ClassNotFoundException e) {
            log.info("找不到策略类：", e);
            throw new NotFoundException("找不到该策略！");
        } catch (NoSuchMethodException e) {
            log.info("该策略类无默认构造方法：", e);
            throw new BadRequestException("该策略编写有误！");
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            log.info("无法构造策略对象：", e);
            throw new BadRequestException("该策略编写有误！");
        } catch (BaseException e) {
            log.info("策略参数错误：", e);
            throw new BadRequestException("策略参数错误！");
        }
    }
}
