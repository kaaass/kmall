package net.kaaass.kmall.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.kaaass.kmall.controller.request.OrderCreateRequest;
import net.kaaass.kmall.controller.response.OrderRequestResponse;
import net.kaaass.kmall.dao.entity.OrderEntity;
import net.kaaass.kmall.dao.repository.OrderRepository;
import net.kaaass.kmall.dao.repository.UserAddressRepository;
import net.kaaass.kmall.dto.OrderDto;
import net.kaaass.kmall.exception.ForbiddenException;
import net.kaaass.kmall.exception.InternalErrorExeption;
import net.kaaass.kmall.exception.NotFoundException;
import net.kaaass.kmall.mapper.OrderMapper;
import net.kaaass.kmall.promote.OrderPromoteContext;
import net.kaaass.kmall.promote.PromoteManager;
import net.kaaass.kmall.service.OrderRequestContext;
import net.kaaass.kmall.service.OrderService;
import net.kaaass.kmall.util.Constants;
import net.kaaass.kmall.util.StringUtils;
import org.aspectj.bridge.IMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private PromoteManager promoteManager;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserAddressRepository userAddressRepository;

    @Override
    public OrderEntity getEntityById(String id) throws NotFoundException {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("未找到该订单！"));
    }

    @Override
    public OrderEntity getEntityByIdAndCheck(String id, String uid) throws NotFoundException, ForbiddenException {
        var result = this.getEntityById(id);
        if (!result.getUid().equals(uid)) {
            throw new ForbiddenException("未找到该订单！");
        }
        return null;
    }

    @Override
    public boolean checkRequest(String requestId) {
        return orderRepository.existsByRequestId(requestId);
    }

    @Override
    public OrderDto getById(String id, String uid) throws NotFoundException, ForbiddenException {
        return OrderMapper.INSTANCE.orderEntityToDto(this.getEntityByIdAndCheck(id, uid));
    }

    @Override
    public List<OrderDto> getAllByUid(String uid, Pageable pageable) {
        return orderRepository.findAllByUidOrderByCreateTimeDesc(uid, pageable)
                .stream()
                .map(OrderMapper.INSTANCE::orderEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderRequestResponse createToQueue(String uid, OrderCreateRequest request) throws InternalErrorExeption, NotFoundException {
        var requestId = StringUtils.uuid();
        var context = new OrderRequestContext();
        context.setRequest(request);
        context.setUid(uid);
        context.setRequestId(requestId);
        var result = new OrderRequestResponse();
        result.setRequestId(requestId);
        // 准备上下文
        var mapper = new ObjectMapper();
        String message = null;
        try {
            message = mapper.writeValueAsString(context);
        } catch (JsonProcessingException e) {
            log.warn("序列化错误", e);
            throw new InternalErrorExeption(null, e);
        }
        log.debug("序列化后的订单请求：{}", message);
        this.doCreate(context); // TODO 更改为消息队列
        return result;
    }

    @Override
    public void doCreate(OrderRequestContext context) throws NotFoundException {
        var entity = new OrderEntity();
        entity.setId(StringUtils.orderId(getLastOrderId()));
        entity.setUid(context.getUid());
        entity.setRequestId(context.getRequestId());
        var request = context.getRequest();
        var address = userAddressRepository.findById(request.getAddressId())
                        .filter(addressEntity -> addressEntity.getUid().equals(context.getUid()))
                        .orElseThrow(() -> new NotFoundException("未找到该地址！"));
        entity.setAddress(address);
        var promoteContext = buildPromoteContext(context);
        var promoteResult = promoteManager.doOnOrder(promoteContext);
        entity.setPrice(promoteResult.getPrice());
        entity.setMailPrice(promoteResult.getMailPrice());
        entity.setProducts(promoteResult.getProducts()
                            .stream()
                            .map(OrderMapper.INSTANCE::orderItemDtoToEntity)
                            .collect(Collectors.toList()));
        orderRepository.save(entity);
        // TODO 删除购物车中已有的商品
    }

    private OrderPromoteContext buildPromoteContext(OrderRequestContext context) {
        // TODO
        return null;
    }

    private String getLastOrderId() {
        Timestamp start = Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT));
        Timestamp end = Timestamp.valueOf(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MIDNIGHT));
        log.debug("查询与日期 {} 与 {} 之间", start, end);
        var result = orderRepository.findByCreateTimeBetweenOrderByCreateTimeDesc(start, end);
        return result.map(OrderEntity::getId)
                    .orElse(Constants.INIT_ORDER_ID);
    }
}
