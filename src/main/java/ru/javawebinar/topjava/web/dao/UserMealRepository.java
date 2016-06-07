package ru.javawebinar.topjava.web.dao;

import ru.javawebinar.topjava.model.UserMeal;

import java.util.List;

/**
 * Created by vladimir on 07.06.2016.
 */
public interface UserMealRepository {

    void add(UserMeal u);
    void delete(int id);
    void update(UserMeal u);
    UserMeal getById(int id);
    List<UserMeal> getAll();

}
