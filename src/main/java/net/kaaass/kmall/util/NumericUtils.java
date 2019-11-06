package net.kaaass.kmall.util;

public class NumericUtils {

    public static float moneyRound(float num) {
        return Math.round(num * 100F) / 100F;
    }

    public static float rateRound(float num) {
        return Math.round(num * 100F) / 100F;
    }
}
