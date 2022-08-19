package ru.job4j.store;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.model.User;

import java.util.Optional;

@Repository
@ThreadSafe
public class UserStore implements Store {
    private SessionFactory sessionFactory;

    public UserStore(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<User> save(User user) {
        return tx(session -> {
            Optional<User> rsl = session.createQuery("from User u where u.login = :uLogin ")
                    .setParameter("uLogin", user.getLogin()).uniqueResultOptional();
            if (rsl.isEmpty()) {
                session.save(user);
            }
            return rsl;
        }, sessionFactory);
    }

    public Optional<User> login(String login, String password) {
        return tx(session -> session.createQuery("from User u where u.login = :uLogin and u.password = :uPassword")
                .setParameter("uLogin", login)
                .setParameter("uPassword", password).uniqueResultOptional(), sessionFactory);
    }
}
