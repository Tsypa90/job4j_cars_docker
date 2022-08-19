package ru.job4j.store;

import org.hibernate.SessionFactory;

public class CarStore implements Store {
    private SessionFactory sessionFactory;

    public CarStore(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


}
