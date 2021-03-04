package ru.javawebinar.topjava.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "meals")
@NamedQueries({
        @NamedQuery(name = "Meal.get", query = "SELECT m FROM Meal m WHERE m.id=:id AND m.user.id=:userId"),
        @NamedQuery(name = "Meal.ALL_SORTED", query = "SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC")
//        ,@NamedQuery(name = "Meal.ALL_SORTED", query = "SELECT m FROM Meal m WHERE m.user.id=:userId")
})
public class Meal extends AbstractBaseEntity {
    @Column
    private Integer id;
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    @Column
    private String description;
    @Column
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public User getUser() {
        return user;
    }

    public Meal() {
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public boolean isNew() {
        return id == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return calories == meal.calories &&
                Objects.equals(id, meal.id) &&
                Objects.equals(dateTime, meal.dateTime) &&
                Objects.equals(description, meal.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, dateTime, description, calories);
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
