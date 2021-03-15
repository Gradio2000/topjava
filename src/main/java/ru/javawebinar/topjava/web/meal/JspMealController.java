package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Controller
public class JspMealController {

    @Autowired
    MealRestController controller;

    @GetMapping("/meals")
    public String getMeals(HttpServletRequest request, Model model){
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete" -> {
                int id = Integer.parseInt(request.getParameter("id"));
                controller.delete(id);
                return "redirect:meals";
            }

            case "create", "update" -> {
                int id = Integer.parseInt(request.getParameter("id"));
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        controller.get(id);
                request.setAttribute("meal", meal);
//                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                return "mealForm";
            }

            default -> {
                model.addAttribute("meal", controller.getAll());
                return "meals";
            }
        }
    }
}
