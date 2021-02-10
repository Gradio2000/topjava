package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.DaoImpl;
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

import static org.slf4j.LoggerFactory.getLogger;


@WebServlet("/action")
public class Action extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("into DeleteServlet");
        DaoImpl dao = new DaoImpl();
        int id = Integer.parseInt(req.getParameter("id"));
        dao.delete(id);
        MealServlet mealServlet = new MealServlet();
        mealServlet.doGet(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("into DoPostServlet");
        req.setCharacterEncoding("UTF-8");
        String desc = req.getParameter("description");
        int call = Integer.parseInt(req.getParameter("calories"));
        LocalDateTime localDateTime = LocalDateTime.parse(req.getParameter("date"));

        List<Meal> mealList = MealsUtil.mealList;

        if (req.getParameter("action").equals("update")){
            log.debug("into UpdateServlet");
            int id = Integer.parseInt(req.getParameter("id"));
            for (Meal meal : mealList){
                if (meal.getId() == id){
                    meal.setDescription(desc);
                    meal.setCalories(call);
                    meal.setDateTime(localDateTime);
                }
            }
        }
        else if (req.getParameter("action").equals("add")){
            log.debug("into AddServlet");
            Meal meal = new Meal(localDateTime, desc, call);
            DaoImpl dao = new DaoImpl();
            dao.add(meal);
        }

        MealServlet mealServlet = new MealServlet();
        mealServlet.doGet(req, resp);
    }
}
