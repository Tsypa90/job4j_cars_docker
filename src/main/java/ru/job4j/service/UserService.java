package ru.job4j.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.model.User;
import ru.job4j.store.UserStore;

import java.util.Optional;

@Service
@ThreadSafe
@RequiredArgsConstructor
public class UserService {
    @NonNull
    private final UserStore store;

    public Optional<User> save(User user) {
        return store.save(user);
    }

    public Optional<User> login(String login, String password) {
        return store.login(login, password);
    }
}
