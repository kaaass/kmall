package net.kaaass.kmall.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import net.kaaass.kmall.dao.entity.OrderItemEntity;
import net.kaaass.kmall.service.OrderType;
import net.kaaass.kmall.util.DateToLongSerializer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class OrderDto {

    private String id;

    private String uid;

    private UserAddressDto address;

    private String requestId;

    private OrderType type;

    private float price;

    private float mailPrice;

    private String deliverCode;

    private String reason;

    private List<OrderItemDto> products = new ArrayList<>();

    @JsonSerialize(using = DateToLongSerializer.class)
    private Date createTime;

    @JsonSerialize(using = DateToLongSerializer.class)
    private Date payTime;

    @JsonSerialize(using = DateToLongSerializer.class)
    private Date deliverTime;

    @JsonSerialize(using = DateToLongSerializer.class)
    private Date finishTime;

    @JsonSerialize(using = DateToLongSerializer.class)
    private Date refundTime;
}
