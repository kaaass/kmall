package net.kaaass.kmall.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import net.kaaass.kmall.util.DateToLongSerializer;

import java.util.Date;

@Data
public class CommentDto {

    private String id;

    private String uid;

    private String orderId;

    private String productId;

    private int rate;

    private String content;

    @JsonSerialize(using = DateToLongSerializer.class)
    private Date commentTime;
}
