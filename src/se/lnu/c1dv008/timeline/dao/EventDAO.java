package se.lnu.c1dv008.timeline.dao;


import org.hibernate.Session;
import org.hibernate.Transaction;
import se.lnu.c1dv008.timeline.model.Event;

import java.util.List;

public class EventDAO implements DAO<Event> {

	private final Session session;

	public EventDAO(Session session) {
		this.session = session;
	}

	@Override
	public void save(Event entity) {
		Transaction transaction = session.beginTransaction();
		session.persist(entity);
		transaction.commit();
	}

	@Override
	public void update(Event entity) {
		Transaction transaction = session.beginTransaction();
		session.update(entity);
		transaction.commit();
	}

	@Override
	public void delete(Event entity) {
		Transaction transaction = session.beginTransaction();
		session.delete(entity);
		transaction.commit();
	}

	@Override
	public Event findById(long id) {
		return (Event) session.get(Event.class, id);
	}

	@Override
	public List<Event> findAll() {
		return session.createCriteria(Event.class).list();
	}

	@Override
	public void deleteAll() {
		Transaction transaction = session.beginTransaction();
		List<Event> events = session.createCriteria(Event.class).list();
		for (Event event : events) { //for better performance
			session.delete(event);
		}
		transaction.commit();
	}

}
