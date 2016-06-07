package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by vladimir on 07.06.2016.
 */
public class DateTimeUtil{

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String toString(LocalDateTime dateTime){
        return dateTime.format(formatter);
    }

    public static LocalDateTime fromString(String dateTime){
        return LocalDateTime.parse(dateTime,formatter);
    }
}
