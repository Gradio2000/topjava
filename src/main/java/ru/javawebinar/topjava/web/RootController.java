package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RootController {
    @Autowired
    private UserService service;

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/users")
    public String getUsers(Model model, HttpServletRequest request) {
        if ("update".equals(request.getParameter("action"))){
            int id = Integer.parseInt(request.getParameter("id"));
            model.addAttribute("user", service.get(id));
            return "userForm";
        }
        else {
            model.addAttribute("users", service.getAll());
            return "users";
        }
    }

    @PostMapping("/users")
    public String setUser(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        SecurityUtil.setAuthUserId(userId);
        return "redirect:meals";
    }

    @PostMapping("/user_edit")
    public String userEdit(Model model, HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String roles = request.getParameter("roles");
        User user = service.get(id);
        user.setName(name);
        user.setEmail(email);
        service.update(user);
        model.addAttribute("users", service.getAll());
        return "users";
    }
}
