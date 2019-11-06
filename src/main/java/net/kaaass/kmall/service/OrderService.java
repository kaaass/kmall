package net.kaaass.kmall.service;

import net.kaaass.kmall.controller.request.CommentRequest;
import net.kaaass.kmall.controller.request.OrderCreateRequest;
import net.kaaass.kmall.controller.response.OrderRequestResponse;
import net.kaaass.kmall.dao.entity.OrderEntity;
import net.kaaass.kmall.dto.OrderDto;
import net.kaaass.kmall.exception.BadRequestException;
import net.kaaass.kmall.exception.ForbiddenException;
import net.kaaass.kmall.exception.InternalErrorExeption;
import net.kaaass.kmall.exception.NotFoundException;
import net.kaaass.kmall.vo.UserOrderCountVo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    OrderEntity getEntityById(String id) throws NotFoundException;

    OrderEntity getEntityByIdAndCheck(String id, String uid) throws NotFoundException, ForbiddenException;

    boolean checkRequest(String requestId) throws BadRequestException;

    OrderDto getById(String id, String uid) throws NotFoundException, ForbiddenException;

    List<OrderDto> getAllByUid(String uid, Pageable pageable);

    UserOrderCountVo getUserOrderCount(String uid);

    List<OrderDto> getAllByUidAndType(String uid, OrderType type, Pageable pageable);

    OrderRequestResponse createToQueue(String uid, OrderCreateRequest request) throws InternalErrorExeption, NotFoundException;

    void doCreate(OrderRequestContext context) throws NotFoundException;

    OrderDto setPaid(String id, String uid) throws NotFoundException, ForbiddenException, BadRequestException;

    OrderDto setDelivered(String id, String deliverCode) throws NotFoundException, BadRequestException;

    OrderDto setCanceled(String id, String uid) throws NotFoundException, ForbiddenException, BadRequestException;

    OrderDto setRefunded(String id) throws NotFoundException, BadRequestException;

    OrderDto setCommented(String id, String uid, CommentRequest commentRequest) throws NotFoundException, ForbiddenException, BadRequestException;
}
