package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.user.Profiles;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
//@Profile({"postgres", "datajpa"})
@ActiveProfiles(resolver = Profiles.ActiveDbProfileResolver.class)
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

    @Transactional
    @Override
    public Map<User, List<Meal>> getUserWithMeal(int userId){
        User user = userRepository.getOne(userId);
        List<Meal> mealSet = user.getMealSet().stream()
                .sorted(Comparator.comparing(Meal::getId).reversed())
                .collect(Collectors.toList());

        Map<User, List<Meal>> userSetMap = new HashMap<>();
        userSetMap.put(user, mealSet);
        return userSetMap;
    }

    @Transactional
    @Override
    public Map<Meal, User> getMealById(int id, int userId) {
        Map<Meal, User> mealUserMap = new HashMap<>();
        Meal meal = get(id, userId);
        User user = meal.getUser();
        mealUserMap.put(meal, user);
        return mealUserMap;
    }
}