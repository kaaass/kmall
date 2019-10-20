package net.kaaass.kmall.controller.request;

import lombok.Data;

import java.util.List;

@Data
public class OrderCreateRequest {

    @Data
    public static class CartItem {
        private String id;
    }

    private List<CartItem> cartItems;

    private String addressId;
}
