package net.kaaass.kmall.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.kaaass.kmall.dao.entity.ProductEntity;
import net.kaaass.kmall.dao.entity.PromoteStrategyEntity;
import net.kaaass.kmall.dao.repository.PromoteStrategyRepository;
import net.kaaass.kmall.dto.PromoteStrategyDto;
import net.kaaass.kmall.exception.BadRequestException;
import net.kaaass.kmall.exception.NotFoundException;
import net.kaaass.kmall.mapper.OrderMapper;
import net.kaaass.kmall.mapper.ProductMapper;
import net.kaaass.kmall.mapper.PromoteMapper;
import net.kaaass.kmall.promote.*;
import net.kaaass.kmall.service.ProductService;
import net.kaaass.kmall.service.PromoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PromoteServiceImpl implements PromoteService {

    @Autowired
    private PromoteStrategyRepository promoteStrategyRepository;

    @Autowired
    private PromoteManager promoteManager;

    @Autowired
    private OrderPromoteContextFactory contextFactory;

    @Autowired
    private DbmsPromoteStrategyManager dbmsPromoteStrategyManager;

    @Autowired
    private ServiceAdapter serviceAdapter;

    @Autowired
    private ProductService productService;

    @Override
    public PromoteStrategyDto getById(String promoteId) throws NotFoundException {
        return OrderMapper.INSTANCE.promoteStrategyEntitiyToDto(getEntityById(promoteId));
    }

    @Override
    public PromoteStrategyEntity getEntityById(String promoteId) throws NotFoundException {
        return promoteStrategyRepository.findById(promoteId)
                .orElseThrow(() -> new NotFoundException("未找到此促销策略！"));
    }

    @Override
    public List<PromoteStrategyDto> getAll() {
        return promoteStrategyRepository.findAllByEnabledTrueOrderByOrderAscLastUpdateTimeDesc()
                .stream()
                .map(OrderMapper.INSTANCE::promoteStrategyEntitiyToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PromoteStrategyDto modify(PromoteStrategyDto promoteStrategyDto) {
        var entity = OrderMapper.INSTANCE.promoteStrategyDtoToEntitiy(promoteStrategyDto);
        entity.setLastUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        var result = promoteStrategyRepository.save(entity);
        return OrderMapper.INSTANCE.promoteStrategyEntitiyToDto(result);
    }

    @Override
    public void checkConfigure(String promoteId) throws NotFoundException, BadRequestException {
        var strategyDto = getById(promoteId);
        dbmsPromoteStrategyManager.createFromDbms(strategyDto, serviceAdapter);
    }

    @Override
    public OrderPromoteResult getForSingleProduct(ProductEntity productEntity, int count, String uid, String addressId) throws NotFoundException {
        var productDto = ProductMapper.INSTANCE.productEntityToDto(productEntity);
        var context = contextFactory.buildFromSingleProduct(productDto, count, uid, addressId);
        return promoteManager.doOnOrder(context);
    }

    @Override
    public void deleteById(String promoteId) {
        promoteStrategyRepository.deleteById(promoteId);
    }
}
