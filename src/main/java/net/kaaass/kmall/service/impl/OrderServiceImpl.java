package net.kaaass.kmall.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.kaaass.kmall.KmallApplication;
import net.kaaass.kmall.controller.request.CommentRequest;
import net.kaaass.kmall.controller.request.OrderCreateMultiRequest;
import net.kaaass.kmall.controller.request.OrderCreateRequest;
import net.kaaass.kmall.controller.response.OrderCheckResponse;
import net.kaaass.kmall.controller.response.OrderRequestResponse;
import net.kaaass.kmall.dao.entity.CommentEntity;
import net.kaaass.kmall.dao.entity.OrderEntity;
import net.kaaass.kmall.dao.repository.CartRepository;
import net.kaaass.kmall.dao.repository.CommentRepository;
import net.kaaass.kmall.dao.repository.OrderRepository;
import net.kaaass.kmall.dao.repository.ProductRepository;
import net.kaaass.kmall.dto.OrderDto;
import net.kaaass.kmall.event.AfterOrderPromoteEvent;
import net.kaaass.kmall.event.GotOrderContextEvent;
import net.kaaass.kmall.event.PostOrderContextEvent;
import net.kaaass.kmall.exception.BadRequestException;
import net.kaaass.kmall.exception.ForbiddenException;
import net.kaaass.kmall.exception.InternalErrorExeption;
import net.kaaass.kmall.exception.NotFoundException;
import net.kaaass.kmall.mapper.OrderMapper;
import net.kaaass.kmall.promote.OrderPromoteContextFactory;
import net.kaaass.kmall.promote.PromoteManager;
import net.kaaass.kmall.service.OrderRequestContext;
import net.kaaass.kmall.service.OrderService;
import net.kaaass.kmall.service.OrderType;
import net.kaaass.kmall.service.UserService;
import net.kaaass.kmall.service.mq.OrderMessageProducer;
import net.kaaass.kmall.util.Constants;
import net.kaaass.kmall.util.StringUtils;
import net.kaaass.kmall.vo.UserOrderCountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
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
    private UserService userService;

    @Autowired
    private OrderPromoteContextFactory orderPromoteContextFactory;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderMessageProducer orderMessageProducer;

    @Autowired
    private ObjectMapper objectMapper;

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
        return result;
    }

    @Override
    public OrderCheckResponse checkRequest(String requestId) throws BadRequestException {
        var result = new OrderCheckResponse();
        var order = orderRepository.findByRequestId(requestId);
        if (order.isPresent()) {
            var entity = order.get();
            if (entity.getType() == OrderType.ERROR) {
                throw new BadRequestException(entity.getReason());
            }
            result.setOrderId(entity.getId());
        }
        return result;
    }

    @Override
    public OrderDto getById(String id) throws NotFoundException {
        return OrderMapper.INSTANCE.orderEntityToDto(this.getEntityById(id));
    }

    @Override
    public OrderDto getByIdAndCheck(String id, String uid) throws NotFoundException, ForbiddenException {
        return OrderMapper.INSTANCE.orderEntityToDto(this.getEntityByIdAndCheck(id, uid));
    }

    @Override
    public List<OrderDto> getAll(Pageable pageable) {
        return orderRepository.findAllByTypeIsNotOrderByCreateTimeDesc(OrderType.ERROR, pageable)
                .stream()
                .map(OrderMapper.INSTANCE::orderEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getAllByUid(String uid, Pageable pageable) {
        return orderRepository.findAllByUidAndTypeIsNotOrderByCreateTimeDesc(uid, OrderType.ERROR, pageable)
                .stream()
                .map(OrderMapper.INSTANCE::orderEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserOrderCountVo getUserOrderCount(String uid) {
        var result = new UserOrderCountVo();
        var toPay = orderRepository.countAllByUidAndType(uid, OrderType.CREATED).orElse(0);
        result.setToPay(toPay);
        var toDeliver = orderRepository.countAllByUidAndType(uid, OrderType.PAID).orElse(0);
        result.setToDeliver(toDeliver);
        var toComment = orderRepository.countAllByUidAndType(uid, OrderType.DELIVERED).orElse(0);
        result.setToComment(toComment);
        return result;
    }

    @Override
    public List<OrderDto> getAllByUidAndType(String uid, OrderType type, Pageable pageable) {
        return orderRepository.findAllByUidAndTypeOrderByCreateTimeDesc(uid, type, pageable)
                .stream()
                .map(OrderMapper.INSTANCE::orderEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getAllByType(OrderType type, Pageable pageable) {
        return orderRepository.findAllByTypeOrderByCreateTimeDesc(type, pageable)
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
        // 触发事件
        var event = new PostOrderContextEvent(uid, context);
        KmallApplication.EVENT_BUS.post(event);
        context = event.getContext();
        // 准备上下文
        String message = null;
        try {
            message = this.objectMapper.writeValueAsString(context);
        } catch (JsonProcessingException e) {
            log.warn("序列化错误", e);
            throw new InternalErrorExeption(null, e);
        }
        log.debug("序列化后的订单请求：{}", message);
        this.orderMessageProducer.sendMessage(requestId, message);
        return result;
    }

    @Override
    public void doCreate(OrderRequestContext context) throws NotFoundException {
        var entity = new OrderEntity();
        entity.setId(StringUtils.orderId(getLastOrderId()));
        entity.setUid(context.getUid());
        entity.setRequestId(context.getRequestId());
        var request = context.getRequest();
        var address = userService.getAddressEntityByIdAndCheck(request.getAddressId(), context.getUid());
        var user = userService.getAuthEntityById(context.getUid());
        entity.setAddress(address);
        /*
         打折逻辑
         */
        try {
            // 触发事件
            var event = new GotOrderContextEvent(context);
            var cancel = KmallApplication.EVENT_BUS.post(event);
            context = event.getContext();
            if (cancel) {
                throw new BadRequestException("订单处理被取消！");
            }
            // 拼接上下文
            var promoteContext = orderPromoteContextFactory.buildFromRequestContext(context);
            log.debug("请求上下文：{}", promoteContext);
            // 检查购买限制
            for (var promoteItem : promoteContext.getProducts()) {
                var buyLimit = promoteItem.getProduct().getBuyLimit();
                if (buyLimit > 0 && promoteItem.getCount() > buyLimit) {
                    throw new BadRequestException(String.format("本商品限购%d件！", buyLimit));
                }
                if (new Date().before(promoteItem.getProduct().getStartSellTime())) {
                    throw new BadRequestException("商品还未开卖！");
                }
            }
            // 打折处理
            var promoteResult = promoteManager.doOnOrder(promoteContext);
            log.debug("打折结果：{}", promoteResult);
            // 触发事件
            var promoteEvent = new AfterOrderPromoteEvent(promoteResult);
            cancel = KmallApplication.EVENT_BUS.post(event);
            promoteResult = promoteEvent.getPromoteResult();
            if (cancel) {
                throw new BadRequestException("订单处理被取消！");
            }
            // 处理返回
            entity.setPrice(promoteResult.getPrice());
            entity.setMailPrice(promoteResult.getMailPrice());
            entity.setProducts(promoteResult.getProducts()
                    .stream()
                    .map(OrderMapper.INSTANCE::orderItemDtoToEntity)
                    .peek(orderItemEntity -> orderItemEntity.setUser(user))
                    .peek(orderItemEntity -> orderItemEntity.setOrder(entity))
                    .collect(Collectors.toList()));
            // 检查库存数量
            for (var orderItemEntity : entity.getProducts()) {
                var product = orderItemEntity.getProduct();
                var dest = product.getStorage().getRest() - orderItemEntity.getCount();
                if (dest >= 0) {
                    product.getStorage().setRest(dest);
                } else {
                    throw new BadRequestException("本商品库存不足！");
                }
            }
            // 更新库存
            entity.getProducts().forEach(orderItemEntity -> productRepository.save(orderItemEntity.getProduct()));
        } catch (BadRequestException e) {
            entity.setType(OrderType.ERROR);
            entity.setReason(e.getMessage());
        }
        orderRepository.save(entity);
        /*
          删除购物车中已有的商品
        */
        if (request instanceof OrderCreateMultiRequest) {
            for (var cartItem : ((OrderCreateMultiRequest) request).getCartItems()) {
                cartRepository.deleteById(cartItem.getId());
            }
        }
    }

    @Override
    public OrderDto setPaid(String id, String uid) throws NotFoundException, ForbiddenException, BadRequestException {
        var entity = uid == null ? getEntityById(id) :
                getEntityByIdAndCheck(id, uid);
        if (!entity.getType().less(OrderType.PAID)) {
            throw new BadRequestException("该订单已付款或已取消！");
        }
        entity.setType(OrderType.PAID);
        entity.setPayTime(Timestamp.valueOf(LocalDateTime.now()));
        return OrderMapper.INSTANCE.orderEntityToDto(orderRepository.save(entity));
    }

    @Override
    public OrderDto setDelivered(String id, String deliverCode) throws NotFoundException, BadRequestException {
        var entity = getEntityById(id);
        if (entity.getType() != OrderType.PAID) {
            throw new BadRequestException("只有已付款的订单可以发货！");
        }
        entity.setType(OrderType.DELIVERED);
        entity.setDeliverCode(deliverCode);
        entity.setDeliverTime(Timestamp.valueOf(LocalDateTime.now()));
        return OrderMapper.INSTANCE.orderEntityToDto(orderRepository.save(entity));
    }

    @Override
    public OrderDto setCanceled(String id, String uid) throws NotFoundException, ForbiddenException, BadRequestException {
        var entity = getEntityByIdAndCheck(id, uid);
        if (!entity.getType().less(OrderType.PAID)) {
            throw new BadRequestException("该订单已付款或已取消！");
        }
        entity.setType(OrderType.CANCELED);
        entity.setFinishTime(Timestamp.valueOf(LocalDateTime.now()));
        return OrderMapper.INSTANCE.orderEntityToDto(orderRepository.save(entity));
    }

    @Override
    public OrderDto setRefunded(String id) throws NotFoundException, BadRequestException {
        var entity = getEntityById(id);
        if (entity.getType().less(OrderType.PAID) || entity.getType().great(OrderType.COMMENTED)) {
            throw new BadRequestException("只有已付款、未退款的订单可以退款！");
        }
        entity.setType(OrderType.REFUNDED);
        entity.setRefundTime(Timestamp.valueOf(LocalDateTime.now()));
        return OrderMapper.INSTANCE.orderEntityToDto(orderRepository.save(entity));
    }

    @Override
    public OrderDto setCommented(String id, String uid, CommentRequest commentRequest) throws NotFoundException, ForbiddenException, BadRequestException {
        var entity = getEntityByIdAndCheck(id, uid);
        var auth = userService.getAuthEntityById(uid);
        if (entity.getType() != OrderType.DELIVERED) {
            throw new BadRequestException("该订单当前不能评价！");
        }
        entity.setType(OrderType.COMMENTED);
        entity.setFinishTime(Timestamp.valueOf(LocalDateTime.now()));

        for (var comment : commentRequest.getComments()) {
            var commentEntity = new CommentEntity();
            commentEntity.setUser(auth);
            commentEntity.setOrderId(id);
            commentEntity.setProductId(comment.getProductId());
            commentEntity.setRate(comment.getRate());
            commentEntity.setContent(comment.getContent());
            commentEntity.setCommentTime(Timestamp.valueOf(LocalDateTime.now()));
            commentRepository.save(commentEntity);
        }

        return OrderMapper.INSTANCE.orderEntityToDto(orderRepository.save(entity));
    }

    private String getLastOrderId() {
        Timestamp start = Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT));
        Timestamp end = Timestamp.valueOf(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MIDNIGHT));
        log.debug("查询与日期 {} 与 {} 之间", start, end);
        var result = orderRepository.findFirstByCreateTimeBetweenOrderByCreateTimeDesc(start, end);
        return result.map(OrderEntity::getId)
                    .orElse(Constants.INIT_ORDER_ID);
    }
}
