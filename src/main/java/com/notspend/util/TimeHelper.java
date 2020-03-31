package com.notspend.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeHelper {
    public static long getCurrentEpochTime(){
        LocalDateTime currentTime = LocalDateTime.now();
        ZoneId zoneId = ZoneId.systemDefault();
        return currentTime.atZone(zoneId).toEpochSecond();
    }
}
