package net.kaaass.kmall.controller.request;

import lombok.Data;

@Data
public class OrderCreateSingleRequest extends OrderCreateRequest {

    public final static String TYPE = "SINGLE";

    private String productId;
}
