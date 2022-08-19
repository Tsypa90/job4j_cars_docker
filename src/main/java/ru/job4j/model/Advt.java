package ru.job4j.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "advt")
public class Advt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String descrp;
    private byte[] photo;
    private boolean saled;
    private int price;
    private int run;
    private LocalDateTime created;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Car car;

    public static Advt of(String descrp, byte[] photo, boolean saled, User user, Car car, int price, int run) {
        Advt advt = new Advt();
        advt.created = LocalDateTime.now();
        advt.descrp = descrp;
        advt.photo = photo;
        advt.saled = saled;
        advt.user = user;
        advt.car = car;
        advt.price = price;
        advt.run = run;
        return advt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescrp() {
        return descrp;
    }

    public void setDescrp(String descrp) {
        this.descrp = descrp;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public boolean isSaled() {
        return saled;
    }

    public void setSaled(boolean saled) {
        this.saled = saled;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRun() {
        return run;
    }

    public void setRun(int run) {
        this.run = run;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Advt advt = (Advt) o;
        return id == advt.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
