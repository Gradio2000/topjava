package ru.javawebinar.topjava.repository.jdbc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static ru.javawebinar.topjava.UserTestData.admin;
import static ru.javawebinar.topjava.UserTestData.assertMatch;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class JdbcMealRepositoryTest {

    @Autowired
    private MealService mealService;

    @Test
    public void save() {
        Meal newMeal = MealTestData.NEW_MEAL;
        Meal createdMeal = mealService.create(newMeal, MealTestData.USER_ID);
        int newId = createdMeal.getId();
        MealTestData.assertMatch(createdMeal, newMeal);
    }

    @Test
    public void delete() {
        mealService.delete(MealTestData.MEAL_ID, MealTestData.USER_ID);
        assertThrows(NotFoundException.class, () -> mealService.get(MealTestData.MEAL_ID, MealTestData.USER_ID));
    }

    @Test
    public void get() {
        Meal meal = mealService.get(MealTestData.MEAL_ID, MealTestData.USER_ID);
        MealTestData.assertMatch(meal, MealTestData.MEAL);
    }


    @Test
    public void getAll() {
        List<Meal> list = mealService.getAll(MealTestData.USER_ID);
        MealTestData.assertMatchCollection(list, MealTestData.MEALS_LIST);
    }

    @Test
    public void getBetweenHalfOpen() {
        List<Meal> list = mealService.getBetweenInclusive(LocalDate.of(2021, 01, 29),
                LocalDate.of(2021, 01, 30), MealTestData.USER_ID);
        MealTestData.assertMatchCollection(list, MealTestData.MEALS_LIST_BETWEEN);
    }
}