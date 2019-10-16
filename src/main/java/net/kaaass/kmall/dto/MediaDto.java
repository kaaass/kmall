package net.kaaass.kmall.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import net.kaaass.kmall.util.DateToLongSerializer;

import java.util.Date;

@Data
public class MediaDto {
    private String id;

    private String type;

    private String url;

    private String uploaderUid;

    @JsonSerialize(using = DateToLongSerializer.class)
    private Date uploadTime;
}
