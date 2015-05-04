package se.lnu.c1dv008.timeline.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import se.lnu.c1dv008.timeline.model.Event;
import se.lnu.c1dv008.timeline.model.Timeline;

import java.util.List;

public class TimelineDAO implements DAO<Timeline> {

	private final Session session;

	public TimelineDAO(Session session) {
		this.session = session;
	}

	@Override
	public void save(Timeline entity) {
		Transaction transaction = session.beginTransaction();
		session.persist(entity);
		transaction.commit();
	}

	@Override
	public void update(Timeline entity) {
		Transaction transaction = session.beginTransaction();
		session.update(entity);
		transaction.commit();
	}

	@Override
	public void delete(Timeline entity) {
		Transaction transaction = session.beginTransaction();
		session.delete(entity);
		transaction.commit();
	}

	@Override
	public Timeline findById(long id) {
		return (Timeline) session.get(Timeline.class, id);
	}

	@Override
	public List<Timeline> findAll() {
		return session.createCriteria(Timeline.class).list();
	}

	@Override
	public void deleteAll() {
		Transaction transaction = session.beginTransaction();
		List<Timeline> timelines = session.createCriteria(Event.class).list();
		for (Timeline timeline : timelines) { //for better performance
			session.delete(timeline);
		}
		transaction.commit();
	}

}
