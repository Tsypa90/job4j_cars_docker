package ru.job4j.store;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Advt;

import java.util.List;

@Repository
@ThreadSafe
public class AdvtStore implements Store {
    private SessionFactory sessionFactory;

    public AdvtStore(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Advt save(Advt advt) {
        return tx(session -> {
            session.persist(advt);
            return advt;
        }, sessionFactory);
    }

    public List<Advt> findAll() {
        return tx(session -> {
            var rsl = session.createQuery("from Advt a "
                    + "join fetch a.car ").getResultList();
            return rsl;
        }, sessionFactory);
    }

    public Advt findById(int id) {
        return (Advt) tx(session -> session.createQuery("from Advt a "
                + "join fetch a.car "
                + "where a.id = :aId ").setParameter("aId", id).uniqueResult(), sessionFactory);
    }

    public void isSaled(int id) {
        tx(session -> session.createQuery("update Advt a set a.saled = true where a.id = :aId ")
                        .setParameter("aId", id).executeUpdate(), sessionFactory);
    }

    public void deleteAdvt(int id) {
        tx(session -> session.createQuery("delete Advt a where a.id = :aId ")
                .setParameter("aId", id).executeUpdate(), sessionFactory);
    }

    public void updateAdvt(Advt advt) {
        tx(session ->
                session.createQuery("update Advt a set a.photo = :aPhoto, a.price = :aPrice, a.descrp = :aDescrp where a.id = :aId ")
                        .setParameter("aPhoto", advt.getPhoto())
                        .setParameter("aPrice", advt.getPrice())
                        .setParameter("aDescrp", advt.getDescrp())
                        .setParameter("aId", advt.getId()).executeUpdate(), sessionFactory);
    }

    public List<Advt> myAdvt(int id) {
        return tx(session -> {
            var rsl = session.createQuery("from Advt a "
                    + "join fetch a.user "
                    + "join fetch a.car "
                    + "where a.user.id = :uId")
                    .setParameter("uId", id).getResultList();
            return rsl;
        }, sessionFactory);
    }
}
