package net.kaaass.kmall.vo;

import lombok.Data;
import net.kaaass.kmall.promote.OrderPromoteResult;

@Data
public class ProductExtraVo {

    // TODO 月销

    private String detail;

    private OrderPromoteResult promotes;
}
