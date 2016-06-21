package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.*;

/**
 * Created by vladimir on 21.06.2016.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceTest {

    @Autowired
    private UserMealService userMealService;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void get() throws Exception {
        UserMeal userMeal = userMealService.get(MEAL_ID1, USER_ID);
        MATCHER.assertEquals(MEAL1, userMeal);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        userMealService.get(MEAL_ID1,ADMIN_ID);
    }

    @Test
    public void delete() throws Exception {
        userMealService.delete(MEAL_ID1,USER_ID);
        MATCHER.assertCollectionEquals(Collections.singletonList(MEAL2), userMealService.getAll(USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        userMealService.delete(MEAL_ID1,ADMIN_ID);
    }

    @Test
    public void getBetweenDates() throws Exception {
        MATCHER.assertCollectionEquals(
                Arrays.asList(MEAL1),
                userMealService.getBetweenDates(LocalDate.of(2016,6,21),
                                                LocalDate.of(2016,6,21),USER_ID));
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        MATCHER.assertCollectionEquals(
                Arrays.asList(MEAL2),
                userMealService.getBetweenDateTimes(LocalDateTime.of(2016,6,21,10,1),
                                                    LocalDateTime.of(2016,6,22,21,1),USER_ID));
    }

    @Test
    public void getAll() throws Exception {
        Collection<UserMeal> meals = userMealService.getAll(USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL2, MEAL1), meals);
    }

    @Test
    public void update() throws Exception {
        UserMeal userMeal = new UserMeal(MEAL1);
        userMeal.setCalories(20);
        userMeal.setDateTime(LocalDateTime.of(2000,1,1,1,1,1));
        userMeal.setDescription("updated");
        userMealService.update(userMeal,USER_ID);
        MATCHER.assertEquals(userMeal,userMealService.get(MEAL_ID1,USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() throws Exception {
        userMealService.update(MEAL1,ADMIN_ID);
    }

    @Test
    public void save() throws Exception {
        UserMeal meal = new UserMeal(LocalDateTime.of(2000,1,1,1,1,1), "descr", 30);
        UserMeal created = userMealService.save(meal,USER_ID);
        meal.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL2, MEAL1, meal), userMealService.getAll(USER_ID));
    }

}