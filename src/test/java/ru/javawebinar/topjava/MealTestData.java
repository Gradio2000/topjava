package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final Meal MEAL = new Meal(100002, LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0), "завтрак", 500);
}
