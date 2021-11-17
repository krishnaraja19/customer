package com.volvo.customer.utility;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class Constants {
    public static final ZoneId STOCKHOLM_ZONE_ID = ZoneId.of("Europe/Stockholm");

    public static LocalDateTime getLocalDateTime() {
        return LocalDateTime.now(STOCKHOLM_ZONE_ID);
    }

    private Constants() {

    }
}
