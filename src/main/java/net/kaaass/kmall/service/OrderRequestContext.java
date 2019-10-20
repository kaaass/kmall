package net.kaaass.kmall.service;

import lombok.Data;
import net.kaaass.kmall.controller.request.OrderCreateRequest;

@Data
public class OrderRequestContext {

    private String requestId;

    private String uid;

    private OrderCreateRequest request;
}
