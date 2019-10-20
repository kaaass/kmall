package net.kaaass.kmall.controller.request;

import lombok.Data;

@Data
public class CartAddRequest {

    String productId;

    int count;
}
