package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MealTo {

    private int id;
    private final LocalDateTime dateTime;
    private final String description;
    private final int calories;
    private final boolean excess;

    public MealTo(LocalDateTime dateTime, String description, int calories, boolean excess, int id) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
        this.id = id;
    }

    public int getId() {
        return id;
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

    public boolean isExcess() {
        return excess;
    }

    public String getFormattedDateTime() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(this.dateTime);
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }
}
