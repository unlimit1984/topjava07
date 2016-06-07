package ru.javawebinar.topjava.web.dao;

import ru.javawebinar.topjava.model.UserMeal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by vladimir on 07.06.2016.
 */
public class MemoryRepositoryIml implements UserMealRepository {

    AtomicInteger id;

    Map<Integer, UserMeal> mealMap;

    public MemoryRepositoryIml(List<UserMeal> mealList) {
        this.mealMap = mealList.stream().collect(Collectors.toConcurrentMap(UserMeal::getId, m->m));
        id = new AtomicInteger(1000);
    }

    @Override
    public void add(UserMeal u) {
        u.setId(id.incrementAndGet());
        mealMap.put(u.getId(),u);
    }

    @Override
    public void delete(int id) {
        mealMap.remove(id);
    }

    @Override
    public void update(UserMeal u) {
        mealMap.put(u.getId(),u);
    }

    @Override
    public UserMeal getById(int id) {
        return mealMap.get(id);
    }

    @Override
    public List<UserMeal> getAll() {
        return new ArrayList<>(mealMap.values());
    }
}
