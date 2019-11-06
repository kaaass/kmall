package net.kaaass.kmall.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.kaaass.kmall.util.PromoteStyleSerializer;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromoteStrategyInfoVo {

    private String id;

    private String name;

    private String hint;

    @JsonSerialize(using = PromoteStyleSerializer.class)
    private PromoteStyle style;
}
