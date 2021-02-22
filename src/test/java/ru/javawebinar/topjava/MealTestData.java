package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final Meal MEAL = new Meal(100002, LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0), "завтрак", 500);
    public static final int MEAL_ID = 100002;
    public static final Meal NEW_MEAL = new Meal(LocalDateTime.of(2021, Month.FEBRUARY, 28, 10, 10), "завтрак", 1500);
    public static List<Meal> MEALS_LIST = Arrays.asList(
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 20, 0), "ужин", 410),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 13, 0), "обед", 500),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 10, 0), "завтрак", 1000),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 0, 0), "пограничное значение", 100),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 20, 0), "ужин", 500),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 13, 0), "обед", 1000),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0), "завтрак", 500)
    );

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);

    }

    public static void assertMatchCollection(List<Meal> actual, List<Meal> expected){
        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
    }

}
