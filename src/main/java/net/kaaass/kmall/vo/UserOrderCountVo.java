package net.kaaass.kmall.vo;

import lombok.Data;

@Data
public class UserOrderCountVo {

    private int toPay;

    private int toDeliver;

    private int toComment;
}
