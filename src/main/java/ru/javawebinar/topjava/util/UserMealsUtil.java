package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;


public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<String, Integer> userMealMap = new HashMap<>();
        for (UserMeal userMeal : meals){
            String date = userMeal.getDateTime().getYear() + String.valueOf(userMeal.getDateTime().getMonth())
                    + userMeal.getDateTime().getDayOfMonth();
            userMealMap.merge(date, userMeal.getCalories(), Integer::sum);
        }

        List<UserMealWithExcess> mealWithExcesses = new ArrayList<>();
        for (UserMeal userMeal : meals){
            LocalTime localTime = LocalTime.of(userMeal.getDateTime().getHour(), userMeal.getDateTime().getMinute());
            String date = userMeal.getDateTime().getYear() + String.valueOf(userMeal.getDateTime().getMonth())
                    + userMeal.getDateTime().getDayOfMonth();
            if (TimeUtil.isBetweenHalfOpen(localTime, startTime, endTime)){
                boolean excess = userMealMap.getOrDefault(date, 0) > caloriesPerDay;
                mealWithExcesses.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(),
                        userMeal.getCalories(), excess));
            }
        }
        return mealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumCaloriesByDay = meals.stream()
                .collect(Collectors.groupingBy(s -> s.getDateTime().toLocalDate(), Collectors.summingInt(s -> s.getCalories())));
        return meals.stream()
                .filter(s -> TimeUtil.isBetweenHalfOpen(s.getDateTime().toLocalTime(),startTime, endTime))
                .map(s -> new UserMealWithExcess(s.getDateTime(), s.getDescription(),s.getCalories(), sumCaloriesByDay.get(s.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }


    public static Map<String, Integer> getMap (List<UserMeal> meals){
        return meals.stream()
                .collect(Collectors.toMap (s -> s.getDateTime().getYear() + String.valueOf(s.getDateTime().getMonth())
                                + s.getDateTime().getDayOfMonth(), UserMeal::getCalories, Integer::sum));
    }
}

