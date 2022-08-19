package ru.job4j.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.model.Advt;
import ru.job4j.store.AdvtStore;

import java.time.LocalDateTime;
import java.util.List;

@Service
@ThreadSafe
public class AdvtService {
    private AdvtStore store;

    public AdvtService(AdvtStore store) {
        this.store = store;
    }

    public Advt save(Advt advt) {
        advt.setCreated(LocalDateTime.now());
        advt.setSaled(false);
        return store.save(advt);
    }

    public List<Advt> findAll() {
        return store.findAll();
    }

    public Advt findById(int id) {
        return store.findById(id);
    }

    public void isSaled(int id) {
        store.isSaled(id);
    }

    public void deleteAdvt(int id) {
        store.deleteAdvt(id);
    }

    public void updateAdvt(Advt advt) {
        store.updateAdvt(advt);
    }

    public List<Advt> myAdvt(int id) {
        return store.myAdvt(id);
    }
}
