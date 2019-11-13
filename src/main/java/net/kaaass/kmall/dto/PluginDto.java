package net.kaaass.kmall.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import net.kaaass.kmall.util.DateToLongSerializer;

import java.util.Date;

@Data
public class PluginDto {

    private String id;

    private String filename;

    private boolean enable = true;

    @JsonSerialize(using = DateToLongSerializer.class)
    private Date enableTime;
}
