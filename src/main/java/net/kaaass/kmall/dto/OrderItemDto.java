package net.kaaass.kmall.dto;

import lombok.Data;

@Data
public class OrderItemDto {

    private String id;

    private ProductDto product;

    private float price;

    private int count;
}
