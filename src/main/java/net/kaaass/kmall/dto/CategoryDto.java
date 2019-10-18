package net.kaaass.kmall.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import net.kaaass.kmall.util.DateToLongSerializer;

import java.util.Date;

@Data
public class CategoryDto {
    private String id;

    private String name;

    private CategoryDto parent;

    @JsonSerialize(using = DateToLongSerializer.class)
    private Date createTime;
}
