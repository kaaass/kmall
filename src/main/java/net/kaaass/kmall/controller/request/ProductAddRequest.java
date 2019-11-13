package net.kaaass.kmall.controller.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import net.kaaass.kmall.util.LongToLocalDateTimeDeserializer;

import java.time.LocalDateTime;

@Data
public class ProductAddRequest {

    private String name;

    private String thumbnailId;

    private float price;

    private float mailPrice;

    private int buyLimit;

    private String categoryId;

    @JsonDeserialize(using = LongToLocalDateTimeDeserializer.class)
    private LocalDateTime startSellTime;

    private int rest;
}
