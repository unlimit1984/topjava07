package ru.javawebinar.topjava.web.dao;

import ru.javawebinar.topjava.model.UserMeal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by vladimir on 07.06.2016.
 */
public class MemoryRepositoryIml implements CRUDRepository {


    Map<Integer, UserMeal> mealMap;

    public MemoryRepositoryIml(List<UserMeal> mealList) {
        this.mealMap = mealList.stream().collect(Collectors.toConcurrentMap(UserMeal::getId, m->m));
    }

    @Override
    public void addMeal(UserMeal u) {

        mealMap.put(u.getId(),u);
    }

    @Override
    public void deleteMeal(int id) {
        mealMap.remove(id);
    }

    @Override
    public void updateMeal(UserMeal u) {
        //mealMap.put(u.getId(),u);
        addMeal(u);
    }

    @Override
    public UserMeal getMealById(int id) {
        return mealMap.get(id);
    }

    @Override
    public List<UserMeal> getAllUserMeals() {
        return new ArrayList<>(mealMap.values());
    }
}
