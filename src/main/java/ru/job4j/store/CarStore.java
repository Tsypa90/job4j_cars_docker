package ru.job4j.store;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;

@RequiredArgsConstructor
public class CarStore implements Store {
    @NonNull
    private final SessionFactory sessionFactory;
}
