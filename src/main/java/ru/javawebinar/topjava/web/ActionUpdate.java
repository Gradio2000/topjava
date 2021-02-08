package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

@WebServlet("/action_update")
public class ActionUpdate extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       //to do

//        List<MealTo> mealTos = MealsUtil.filteredByStreams(MealsUtil.mealList,
//                LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
//
//        req.setAttribute("mealsList", mealTos);
//        req.getRequestDispatcher("/meals.jsp").forward(req, resp);

        MealServlet mealServlet = new MealServlet();
        mealServlet.doGet(req, resp);
    }
}
