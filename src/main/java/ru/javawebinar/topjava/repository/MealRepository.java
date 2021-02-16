package ru.javawebinar.topjava.repository;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;


public interface MealRepository {
    // null if updated meal do not belong to userId
    Meal save(Meal meal);

    // false if meal do not belong to userId
    boolean delete(int id);

    // null if meal do not belong to userId
    Meal get(int id);

    // ORDERED dateTime desc
    Collection<Meal> getAll();
}
