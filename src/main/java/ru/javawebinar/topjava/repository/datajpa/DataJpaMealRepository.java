package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DataJpaMealRepository implements MealRepository {

    private final CrudMealRepository crudRepository;

    public DataJpaMealRepository(CrudMealRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        return null;
    }

    @Transactional
    @Override
    public boolean delete(int id, int userId) {
        Meal meal = get(id, userId);
        if (meal.getUser().id() != userId){
            return false;
        }
        else {
            crudRepository.delete(meal);
            return true;
        }
    }

    @Transactional
    @Override
    public Meal get(int id, int userId) {
        Meal meal = crudRepository.findById(id).orElse(null);
        if (meal == null || meal.getUser() == null ||  meal.getUser().id() != userId){
            throw new NotFoundException("aaa");
        }
        return meal;
    }

    @Transactional
    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.findAll().stream()
                .filter(meal -> meal.getUser().id() == userId)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudRepository.findAll().stream()
                .filter(meal -> meal.getUser().id() == userId)
                .filter(meal -> meal.getDateTime().isAfter(startDateTime))
                .filter(meal -> meal.getDateTime().isBefore(endDateTime))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}