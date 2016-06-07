package ru.javawebinar.topjava.model;

import lombok.Data;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * GKislin
 * 11.01.2015.
 */
@Data
public class UserMealWithExceed {

    private final LocalDateTime dateTime;
    private final String description;
    private final int calories;
    private final boolean exceed;
    private final int id;

    private String formatDateTime;
    public String getFormatDateTime(){
        return DateTimeUtil.toString(dateTime);
    }
}
