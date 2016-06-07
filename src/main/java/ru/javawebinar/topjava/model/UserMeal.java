package ru.javawebinar.topjava.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserMeal {
    protected final LocalDateTime dateTime;
    protected final String description;
    protected final int calories;
    protected int id;

}
