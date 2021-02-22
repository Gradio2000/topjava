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

    private void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);

    }


    @Autowired
    private MealService mealService;


    @Test
    public void save() {
        Meal newMeal = MealTestData.NEW_MEAL;
        Meal createdMeal = mealService.create(newMeal, MealTestData.USER_ID);
        int newId = createdMeal.getId();
        assertMatch(createdMeal, newMeal);
    }

    @Test
    public void delete() {
        mealService.delete(MealTestData.MEAL_ID, MealTestData.USER_ID);
        assertThrows(NotFoundException.class, () -> mealService.get(MealTestData.MEAL_ID, MealTestData.USER_ID));
    }

    @Test
    public void get() {
        Meal meal = mealService.get(MealTestData.MEAL_ID, MealTestData.USER_ID);
        assertMatch(meal, MealTestData.MEAL);
    }


    @Test
    public void getAll() {

    }

    @Test
    public void getBetweenHalfOpen() {
    }
}