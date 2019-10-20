package net.kaaass.kmall.dto;

import lombok.Data;

@Data
public class ProductDto {
    private String id;

    private String name;

    private MediaDto thumbnail;

    private float price;

    private float mailPrice;

    private int buyLimit;

    private CategoryDto category;

    private ProductStorageDto storage;
}
