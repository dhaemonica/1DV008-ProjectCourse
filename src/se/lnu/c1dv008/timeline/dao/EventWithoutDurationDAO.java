package se.lnu.c1dv008.timeline.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import se.lnu.c1dv008.timeline.model.EventWithoutDuration;

import java.util.List;

/**
 * Created by otto on 2015-05-15.
 */
public class EventWithoutDurationDAO implements DAO<EventWithoutDuration> {

    private final Session session;

    public EventWithoutDurationDAO(Session session) {
        this.session = session;
    }

    @Override
    public void save(EventWithoutDuration entity) {
        Transaction transaction = session.beginTransaction();
        session.persist(entity);
        transaction.commit();
    }

    @Override
    public void update(EventWithoutDuration entity) {
        Transaction transaction = session.beginTransaction();
        session.update(entity);
        transaction.commit();
    }

    @Override
    public void delete(EventWithoutDuration entity) {
        Transaction transaction = session.beginTransaction();
        session.delete(entity);
        transaction.commit();
    }

    @Override
    public EventWithoutDuration findById(long id) {
        return (EventWithoutDuration) session.get(EventWithoutDuration.class, id);
    }

    @Override
    public List<EventWithoutDuration> findAll() {
        return session.createCriteria(EventWithoutDuration.class).list();
    }

    @Override
    public void deleteAll() {
        Transaction transaction = session.beginTransaction();
        List<EventWithoutDuration> events = session.createCriteria(EventWithoutDuration.class).list();
        //for better performance
        events.forEach(session::delete);
        transaction.commit();
    }

}

