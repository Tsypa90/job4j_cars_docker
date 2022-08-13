package ru.job4j.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "advt")
public class Advt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String descrp;
    private String brand;
    private String body;
    private byte[] photo;
    private boolean saled;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public static Advt of(String descrp, String brand, String body, byte[] photo) {
        Advt advt = new Advt();
        advt.descrp = descrp;
        advt.brand = brand;
        advt.body = body;
        advt.photo = photo;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
