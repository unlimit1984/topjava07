package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {
    private AtomicInteger counter = new AtomicInteger(0);
    private Map<Integer, Map<Integer, UserMeal>> repository = new ConcurrentHashMap<>();

    {
        UserMealsUtil.MEAL_LIST.forEach((userMeal) -> save(userMeal, LoggedUser.id()));
    }

    @Override
    public synchronized UserMeal save(UserMeal userMeal, int userId) {
        Map<Integer, UserMeal> mealRepository = repository.get(userId);
        if (mealRepository == null) {
            mealRepository = new ConcurrentHashMap<>();
        }

        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
            mealRepository.put(userMeal.getId(), userMeal);
            repository.put(userId, mealRepository);
        } else if (mealRepository.containsKey(userMeal.getId())) {
            mealRepository.put(userMeal.getId(), userMeal);
            repository.put(userId, mealRepository);
        } else {
            return null;
        }
        return userMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return repository.getOrDefault(userId, Collections.emptyMap()).remove(id) != null;
    }

    @Override
    public UserMeal get(int id, int userId) {
        return repository.getOrDefault(userId, Collections.emptyMap()).get(id);
    }

    @Override
    public Collection<UserMeal> getAll(int userId) {
        return repository.getOrDefault(userId,Collections.emptyMap()).values()
                .stream()
                .sorted((um1, um2) -> um2.getDateTime().compareTo(um1.getDateTime()))
                .collect(Collectors.toList());
    }
}

