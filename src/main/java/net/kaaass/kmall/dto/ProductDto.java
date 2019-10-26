package net.kaaass.kmall.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import net.kaaass.kmall.util.DateToLongSerializer;

import java.util.Date;

@Data
public class ProductDto {
    private String id;

    private String name;

    private MediaDto thumbnail;

    private float price;

    private float mailPrice;

    private int buyLimit;

    private CategoryDto category;

    private int indexOrder;

    private ProductStorageDto storage;

    @JsonSerialize(using = DateToLongSerializer.class)
    private Date startSellTime;
}
