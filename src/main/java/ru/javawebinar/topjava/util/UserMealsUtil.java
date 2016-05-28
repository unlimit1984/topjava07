package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {

    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        //getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);

        System.out.println(getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field

        //Java7
//        Map<LocalDate,Integer> mealCounter = new HashMap<>();
//        for(UserMeal userMeal : mealList){
//            Integer calories = mealCounter.get(userMeal.getDateTime().toLocalDate());
//
//            mealCounter.put( userMeal.getDateTime().toLocalDate(),
//                            (calories==null ? 0 : calories) + userMeal.getCalories());
//        }
        //Java 8 Stream API
        Map<LocalDate, Integer> mealCounter = new HashMap<>();
        mealList
                .stream()
                .forEach(m -> mealCounter.put(m.getDateTime().toLocalDate(),
                        Optional.ofNullable(mealCounter.get(m.getDateTime().toLocalDate())).orElse(0) + m.getCalories()));

        //Java7
//        List<UserMealWithExceed> result = new ArrayList<>();
//        for(UserMeal userMeal : mealList){
//            if(TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(),startTime,endTime)){
//                result.add(new UserMealWithExceed(  userMeal,
//                                                    mealCounter.get(userMeal.getDateTime().toLocalDate())>caloriesPerDay));
//            }
//        }

        //Java 8 Stream API
        List<UserMealWithExceed> result =
                mealList.stream()
                        .filter(m -> TimeUtil.isBetween(m.getDateTime().toLocalTime(), startTime, endTime))
                        .map(u -> new UserMealWithExceed(u, mealCounter.get(u.getDateTime().toLocalDate()) > caloriesPerDay))
                        .collect(Collectors.toList());
        return result;
    }
}
