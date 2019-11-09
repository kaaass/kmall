package net.kaaass.kmall.controller.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.util.List;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(
                value = OrderCreateMultiRequest.class,
                name = OrderCreateMultiRequest.TYPE),
        @JsonSubTypes.Type(
                value = OrderCreateSingleRequest.class,
                name = OrderCreateSingleRequest.TYPE)
})
public abstract class OrderCreateRequest {

    private String addressId;
}
