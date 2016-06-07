package ru.javawebinar.topjava.web.dao;

import ru.javawebinar.topjava.model.UserMeal;

import java.util.List;

/**
 * Created by vladimir on 07.06.2016.
 */
public interface CRUDRepository {

    void addMeal(UserMeal u);
    void deleteMeal(int id);
    void updateMeal(UserMeal u);
    UserMeal getMealById(int id);
    List<UserMeal> getAllUserMeals();

}
