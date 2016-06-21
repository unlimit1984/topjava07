package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(UserMeal::toString);

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final int MEAL_ID1 = START_SEQ+2;
    public static final int MEAL_ID2 = START_SEQ+3;

    public static final UserMeal MEAL1 = new UserMeal(MEAL_ID1, LocalDateTime.of(2016,06,21,10,0),"Завтрак", 300);
    public static final UserMeal MEAL2 = new UserMeal(MEAL_ID2, LocalDateTime.of(2016,06,22,21,0),"Ужин", 600);

}
