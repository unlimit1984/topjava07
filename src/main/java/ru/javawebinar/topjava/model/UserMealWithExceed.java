package ru.javawebinar.topjava.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * GKislin
 * 11.01.2015.
 */
@Data
public class UserMealWithExceed {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final LocalDateTime dateTime;
    private final String description;
    private final int calories;
    private final boolean exceed;
    private final int id;

    private String formatDateTime;
    public String getFormatDateTime(){
        return dateTime.format(formatter);
    }
}
