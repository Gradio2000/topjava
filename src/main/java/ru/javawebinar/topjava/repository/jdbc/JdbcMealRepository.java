package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JdbcMealRepository implements MealRepository {

    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = new BeanPropertyRowMapper<>(Meal.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert mealsInsert;

    @Autowired
    public JdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.mealsInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.getId() == null){
            jdbcTemplate.update("INSERT INTO meals (datetime, description, calories, userid) " +
                    "VALUES (?, ?, ?, ?)", meal.getDateTime(), meal.getDescription(), meal.getCalories(), userId);
        }
        else {
            Meal mealToUp = get(meal.getId(), userId);
            mealToUp.setDateTime(meal.getDateTime());
            mealToUp.setDescription(meal.getDescription());
            mealToUp.setCalories(meal.getCalories());

            jdbcTemplate.update("UPDATE meals SET (datetime, description, calories) = (?, ?, ?) WHERE id = ?", mealToUp.getDateTime(),
                    mealToUp.getDescription(), mealToUp.getCalories(), meal.getId());
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id=? AND userid=?", id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE id=? AND userid = ?", ROW_MAPPER, id, userId).stream().findAny().orElse(null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE userid = ? ORDER BY datetime DESC", ROW_MAPPER, userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE userid=? AND datetime>=? AND datetime<=? ORDER BY datetime DESC", ROW_MAPPER, userId, startDateTime, endDateTime);
    }

}
