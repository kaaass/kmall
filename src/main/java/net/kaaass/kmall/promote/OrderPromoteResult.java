package net.kaaass.kmall.promote;

import lombok.Data;
import net.kaaass.kmall.dto.OrderItemDto;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderPromoteResult {

    private float price;

    private float mailPrice;

    private List<OrderItemDto> products = new ArrayList<>();
}
