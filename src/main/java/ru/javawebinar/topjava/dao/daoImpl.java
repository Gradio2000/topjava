package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

public class daoImpl implements dao{
    @Override
    public Meal update(Meal editMeal) {
        List<Meal> mealsList = MealsUtil.mealList;
        for (Meal meal : mealsList){
            if (meal.getId() == editMeal.getId()){
                meal.setDateTime(editMeal.getDateTime());
                meal.setCalories(editMeal.getCalories());
                meal.setDescription(editMeal.getDescription());
            }
        }
        return null;
    }
}
