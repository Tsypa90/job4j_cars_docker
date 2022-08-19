package ru.job4j.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.model.User;
import ru.job4j.store.UserStore;

import java.util.Optional;

@Service
@ThreadSafe
public class UserService {
    private UserStore store;

    public UserService(UserStore store) {
        this.store = store;
    }

    public Optional<User> save(User user) {
        return store.save(user);
    }

    public Optional<User> login(String login, String password) {
        return store.login(login, password);
    }
}
