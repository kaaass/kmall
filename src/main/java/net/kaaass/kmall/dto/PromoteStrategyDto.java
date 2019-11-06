package net.kaaass.kmall.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import net.kaaass.kmall.util.DateToLongSerializer;
import net.kaaass.kmall.vo.PromoteStyle;

import java.util.Date;

@Data
public class PromoteStrategyDto {

    private String id;

    private String name;

    private String hint;

    private String clazz;

    private String param;

    private int order;

    private PromoteStyle style;

    private boolean enabled = true;

    @JsonSerialize(using = DateToLongSerializer.class)
    private Date lastUpdateTime;
}
