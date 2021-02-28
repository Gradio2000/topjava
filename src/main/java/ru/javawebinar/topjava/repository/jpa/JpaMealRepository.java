package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if (userId != SecurityUtil.authUserId()){
            throw new NotFoundException("Некорректный пользователь");
        }

        User user = entityManager.getReference(User.class, userId);
        meal.setUser(user);

        if (meal.isNew()){
            entityManager.persist(meal);
            return meal;
        }
        else {
            return entityManager.merge(meal);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        if(get(id, userId) == null){
            throw new NotFoundException("ошибка");
        }

        Query query = entityManager.createQuery("DELETE FROM Meal m WHERE m.id=" + id);
        return query.executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {

        return entityManager.createNamedQuery("Meal.get", Meal.class)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .getResultList()
                .stream()
                .findAny().orElse(null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return entityManager.createNamedQuery("Meal.ALL_SORTED", Meal.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return entityManager.createQuery("SELECT m FROM Meal AS m WHERE m.user.id=:userId AND m.dateTime>=:start AND m.dateTime<:end ORDER BY m.dateTime DESC ", Meal.class)
                .setParameter("userId", userId)
                .setParameter("start", startDateTime)
                .setParameter("end", endDateTime)
                .getResultList();
    }
}