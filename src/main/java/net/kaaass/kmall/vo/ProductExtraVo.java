package net.kaaass.kmall.vo;

import lombok.Data;
import net.kaaass.kmall.dto.MediaDto;
import net.kaaass.kmall.promote.OrderPromoteResult;

import java.util.List;

@Data
public class ProductExtraVo {

    private int monthPurchase;

    private String detail;

    private OrderPromoteResult promotes;

    private List<MediaDto> images;

    private List<ActualTemplateVo> template;
}
