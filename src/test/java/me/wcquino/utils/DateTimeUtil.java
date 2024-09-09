package me.wcquino.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUtil {
    public static String getCurrentDate() {
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }

    public static String getFutureDate() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime futureDate = today.plusDays(1);

        DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return formatterData.format(futureDate);
    }
}
