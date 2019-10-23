package net.kaaass.kmall.controller;

import lombok.extern.slf4j.Slf4j;
import net.kaaass.kmall.controller.request.CommentRequest;
import net.kaaass.kmall.controller.request.OrderCreateRequest;
import net.kaaass.kmall.controller.response.OrderRequestResponse;
import net.kaaass.kmall.dto.OrderDto;
import net.kaaass.kmall.exception.BadRequestException;
import net.kaaass.kmall.exception.ForbiddenException;
import net.kaaass.kmall.exception.InternalErrorExeption;
import net.kaaass.kmall.exception.NotFoundException;
import net.kaaass.kmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@PreAuthorize("hasRole('USER')")
@Slf4j
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/request/{requestId}/")
    boolean checkRequest(@PathVariable String requestId) {
        return orderService.checkRequest(requestId);
    }

    @GetMapping("/{id}/")
    OrderDto getById(@PathVariable String id) throws NotFoundException, ForbiddenException {
        return orderService.getById(id, getUid());
    }

    @GetMapping("/")
    List<OrderDto> getAllByUid(Pageable pageable) {
        return orderService.getAllByUid(getUid(), pageable);
    }

    @PostMapping("/")
    OrderRequestResponse createToQueue(@RequestBody OrderCreateRequest request) throws NotFoundException, InternalErrorExeption {
        // TODO 参数检查
        return orderService.createToQueue(getUid(), request);
    }

    /**
     * 外部支付回调
     */
    @GetMapping("/{id}/payCheck/")
    OrderDto payCheck(@PathVariable String id, @RequestParam String callback) throws NotFoundException, ForbiddenException, BadRequestException {
        // 检查callback
        return orderService.setPaid(id, getUid());
    }

    @PostMapping("/{id}/pay/")
    @PreAuthorize("hasRole('ADMIN')")
    OrderDto setPaid(@PathVariable String id) throws NotFoundException, ForbiddenException, BadRequestException {
        return orderService.setPaid(id, null);
    }

    @PostMapping("/{id}/deliver/")
    @PreAuthorize("hasRole('ADMIN')")
    OrderDto setDelivered(@PathVariable String id, @RequestParam String deliverCode) throws NotFoundException, BadRequestException {
        // TODO 检查输入
        return orderService.setDelivered(id, deliverCode);
    }

    @PostMapping("/{id}/cancel/")
    OrderDto setCanceled(@PathVariable String id) throws BadRequestException, NotFoundException, ForbiddenException {
        return orderService.setCanceled(id, getUid());
    }

    @PostMapping("/{id}/refund/")
    @PreAuthorize("hasRole('ADMIN')")
    OrderDto setRefunded(@PathVariable String id) throws NotFoundException, BadRequestException {
        return orderService.setRefunded(id);
    }

    @PostMapping("/{id}/comment/")
    OrderDto setCommented(@PathVariable String id, @RequestBody CommentRequest commentRequest) throws NotFoundException, BadRequestException, ForbiddenException {
        // TODO 校验
        return orderService.setCommented(id, getUid(), commentRequest);
    }
}
