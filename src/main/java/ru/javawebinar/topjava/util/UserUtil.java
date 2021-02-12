package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserUtil {
    public static final List<User> users = new ArrayList<>();

    {
        users.add(new User(null, "Alex", "aaa@aa.aa", "aaaa", Role.USER));
    }
}
