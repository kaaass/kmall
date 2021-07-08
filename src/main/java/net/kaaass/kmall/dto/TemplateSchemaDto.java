package net.kaaass.kmall.dto;

import lombok.Data;

import java.util.List;

/**
 * 模板定义 DTO。JSON序列化后持久化在数据库中。
 * @author kaaass
 */
@Data
public class TemplateSchemaDto {

    String group;

    List<String> columns;
}
