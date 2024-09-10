package me.wcquino.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final ZoneId ZONE_ID = ZoneId.of("America/Sao_Paulo");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static String formatDate(LocalDateTime dateTime) {
        ZonedDateTime zonedDate = dateTime.atZone(ZONE_ID);
        return DATE_FORMATTER.format(zonedDate);
    }

    public static String getCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        return formatDate(now);
    }

    public static String getFutureDate() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(1);
        return formatDate(futureDate);
    }
}
