package ru.job4j;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Advt;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class AdRepository {
    private SessionFactory sessionFactory;

    public AdRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Advt> lastDayAdvt() {
        LocalDateTime lastDay = LocalDateTime.now().toLocalDate().minusDays(1).atStartOfDay();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        var result = session.createQuery("select a from Advt a "
                        + "join fetch a.user "
                        + "where a.created between :lastDay and :curDay", Advt.class)
                .setParameter("lastDay", lastDay)
                .setParameter("curDay", LocalDateTime.now())
                .getResultList();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public List<Advt> withPhoto() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        var result = session.createQuery("select a from Advt a "
                + "join fetch a.user "
                + "where a.photo is not EMPTY ", Advt.class).getResultList();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public List<Advt> defBrand(String brand) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
         var result = session.createQuery("select a from Advt a "
                + "join fetch a.user "
                + "where a.brand like :aBrand ", Advt.class)
                .setParameter("aBrand", brand).getResultList();
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
