package net.kaaass.kmall.controller.request;

import lombok.Data;

@Data
public class ProductAddRequest {

    private String name;

    private String thumbnailId;

    private float price;

    private float mailPrice;

    private int buyLimit;

    private String categoryId;
}
