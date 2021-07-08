package net.kaaass.kmall.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TimeUtils {

    public static Timestamp nowTimestamp() {
        return Timestamp.valueOf(LocalDateTime.now());
    }
}
