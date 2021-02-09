package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

public interface Dao {
    Meal update(Meal meal);
    void delete(int id);
    void add(Meal meal);
}
