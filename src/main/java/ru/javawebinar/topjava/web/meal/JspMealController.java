package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController {

    @Autowired
    MealRestController controller;

    @GetMapping("/meals")
    public String getMeals(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete" -> {
                int id = Integer.parseInt(request.getParameter("id"));
                controller.delete(id);
                return "redirect:meals";
            }

            case "update" -> {
                int id = Integer.parseInt(request.getParameter("id"));
                Meal meal = controller.get(id);
                request.setAttribute("meal", meal);
                return "mealForm";
            }

            case "create" -> {
                Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
                request.setAttribute("meal", meal);
                return "mealForm";
            }

            case "filter" -> {
                LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
                LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
                LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
                LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
                model.addAttribute ("meal", controller.getBetween(startDate, startTime, endDate, endTime));
                return "meals";
            }

            default -> {
                model.addAttribute("meal", controller.getAll());
                return "meals";
            }
        }
    }

    @PostMapping("/meals")
    public String saveMeal(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        Map<String, String[]> map = request.getParameterMap();
        String[] mass = map.get("id");

         if (mass[0] != ""){
            controller.update(meal, Integer.parseInt(request.getParameter("id")));
        }
        else {
            controller.create(meal);
        }
        model.addAttribute("meal", controller.getAll());
        return "meals";
    }
}
