package net.kaaass.kmall.controller.request;

import lombok.Data;

@Data
public class CategoryAddRequest {

    private String name;

    private String parentId;
}
