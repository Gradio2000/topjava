package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Profile("postgres")
public class DataJpaMealRepository implements MealRepository {

    private final CrudUserRepository userRepository;
    private final CrudMealRepository crudRepository;

    public DataJpaMealRepository(CrudMealRepository crudRepository, CrudUserRepository userRepository) {
        this.crudRepository = crudRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public Meal save(Meal meal, int userId) {
        User user = userRepository.getOne(userId);
        meal.setUser(user);
        if (meal.isNew()){
            return crudRepository.save(meal);
        }
        else {
            Meal updateMeal = get(meal.getId(), userId);
            updateMeal.setDateTime(meal.getDateTime());
            updateMeal.setDescription(meal.getDescription());
            updateMeal.setCalories(meal.getCalories());
            return crudRepository.save(updateMeal);
        }
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
            throw new NotFoundException("Not found entity with id=" + id);
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