package ru.javawebinar.topjava.web.user;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserTestService;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class UserTestController{

    UserTestService userTestService;

    public UserTestController(UserTestService userTestService) {
        this.userTestService = userTestService;
    }

    public User create(User user) {
        checkNew(user);
        return userTestService.create(user);
    }


    public boolean delete(int userId) {
        return userTestService.delete(userId);
    }
}
