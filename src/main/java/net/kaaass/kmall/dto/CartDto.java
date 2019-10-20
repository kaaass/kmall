package net.kaaass.kmall.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import net.kaaass.kmall.util.DateToLongSerializer;

import java.util.Date;

@Data
public class CartDto {
    private String id;

    private String uid;

    private ProductDto product;

    private int count;

    @JsonSerialize(using = DateToLongSerializer.class)
    private Date createTime;
}
