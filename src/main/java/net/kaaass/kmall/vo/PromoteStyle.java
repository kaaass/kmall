package net.kaaass.kmall.vo;

import lombok.Getter;

public enum PromoteStyle {

    INFO("badge-success"),

    HOT("badge-danger"),

    GREAT("badge-warning"),

    NORMAL("badge-info");

    @Getter
    private String style;

    PromoteStyle(String style) {
        this.style = style;
    };
}
