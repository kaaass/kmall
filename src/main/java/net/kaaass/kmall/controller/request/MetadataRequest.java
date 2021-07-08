package net.kaaass.kmall.controller.request;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class MetadataRequest {

    @Size(
            message = "键长度必须为1~50",
            min = 1,
            max = 50
    )
    private String key;

    private String value;
}
