package net.kaaass.kmall.controller.request;

import lombok.Data;

import java.util.List;

@Data
public class OrderCreateMultiRequest extends OrderCreateRequest {

    public final static String TYPE = "MULTI";

    @Data
    public static class CartItem {
        private String id;
    }

    private List<CartItem> cartItems;
}
