package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


@WebServlet("/action_update")
public class ActionUpdate extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(req.getParameter("id"));
        String desc = req.getParameter("description");
        int call = Integer.parseInt(req.getParameter("calories"));
        LocalDateTime localDateTime = LocalDateTime.parse(req.getParameter("date"));

        List<Meal> mealList = MealsUtil.mealList;
        for (Meal meal : mealList){
            if (meal.getId() == id){
                meal.setDescription(desc);
                meal.setCalories(call);
                meal.setDateTime(localDateTime);
            }
        }

        MealServlet mealServlet = new MealServlet();
        mealServlet.doGet(req, resp);
    }
}
