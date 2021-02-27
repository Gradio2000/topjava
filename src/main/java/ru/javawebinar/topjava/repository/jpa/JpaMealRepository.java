package ru.javawebinar.topjava.repository.jpa;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.table.TableRowSorter;
import java.time.LocalDateTime;
import java.util.List;

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
        if (userId != SecurityUtil.authUserId()){
           throw new NotFoundException("Некорректный пользователь");
        }
        return entityManager.createNamedQuery("Meal.delete")
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        if (userId != SecurityUtil.authUserId()){
            throw new NotFoundException("Некорректный юзер");
        }
        return entityManager.createNamedQuery("Meal.get", Meal.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findAny().orElse(null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return entityManager.createNamedQuery("Meal.getAll", Meal.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return null;
    }
}