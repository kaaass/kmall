package net.kaaass.kmall.vo;

import lombok.Data;
import net.kaaass.kmall.promote.OrderPromoteResult;

@Data
public class ProductExtraVo {

    private int monthPurchase;

    private String detail;

    private OrderPromoteResult promotes;
}
