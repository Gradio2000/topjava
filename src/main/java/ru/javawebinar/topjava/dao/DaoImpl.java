package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Iterator;
import java.util.List;

public class DaoImpl implements Dao {
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

    @Override
    public void delete(int id) {
        List<Meal> mealsList = MealsUtil.mealList;
        Iterator<Meal> iterator = mealsList.iterator();
        while (iterator.hasNext()){
            Meal meal = iterator.next();
            if (meal.getId() == id){
                iterator.remove();
            }
        }
    }


    @Override
    public void add(Meal meal) {
        List<Meal> mealsList = MealsUtil.mealList;
        mealsList.add(meal);
    }
}
