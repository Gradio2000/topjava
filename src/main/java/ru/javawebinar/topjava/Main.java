package ru.javawebinar.topjava;


import org.springframework.jdbc.core.JdbcTemplate;
import ru.javawebinar.topjava.model.Meal;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String URL = "jdbc:postgresql://localhost:5433/topjava";
    private static final String USERNAME = "user";
    private static final String PASSWORD= "password";

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public static List<Meal> getAll() throws SQLException {
        List<Meal> mealList = new ArrayList<>();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM meals";
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()){
            Meal meal = new Meal();
             meal.setId(resultSet.getInt("id"));
             meal.setCalories(resultSet.getInt("calories"));
             meal.setDescription(resultSet.getString("description"));
             mealList.add(meal);
        }
        return mealList;
    }

    public static void main(String[] args) throws SQLException {
        List<Meal> mealList = getAll();
        for (Meal meal : mealList){
            System.out.println(meal.getId());
            System.out.println(meal.getDescription());
            System.out.println(meal.getCalories());
        }
    }
}
