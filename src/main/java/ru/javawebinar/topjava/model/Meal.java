package ru.javawebinar.topjava.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "meals")
@NamedQueries({
        @NamedQuery(name = "Meal.getAll", query = "SELECT m FROM Meal m WHERE m.user.id=:userId"),
        @NamedQuery(name = "Meal.delete", query = "DELETE FROM Meal m WHERE m.id=:id"),
        @NamedQuery(name = "Meal.get", query = "SELECT m FROM Meal m WHERE m.id=:id"),
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

    @ManyToOne(
            fetch = FetchType.LAZY)
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
        super(id);
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
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
