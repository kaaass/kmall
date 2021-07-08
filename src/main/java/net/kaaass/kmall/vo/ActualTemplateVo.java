package net.kaaass.kmall.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 模板内容 DTO。JSON序列化后持久化在数据库中。
 * @author kaaass
 */
@Data
public class ActualTemplateVo {

    String group;

    Map<String, String> columns;
}
