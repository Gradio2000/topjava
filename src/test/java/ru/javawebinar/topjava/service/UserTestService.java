package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;

@Service
public class UserTestService {

    InMemoryUserRepository userRepository;

    public UserTestService(InMemoryUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        return userRepository.save(user);
    }


    public boolean delete(int userId) {
        return userRepository.delete(userId);
    }
}
