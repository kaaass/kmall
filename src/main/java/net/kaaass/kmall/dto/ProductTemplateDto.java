package net.kaaass.kmall.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductTemplateDto {

    private String id;

    private String name;

    private List<TemplateSchemaDto> schema;
}
