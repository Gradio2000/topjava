package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

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
        }

        model.addAttribute("meal", controller.getAll());
        return "meals";
    }

}
