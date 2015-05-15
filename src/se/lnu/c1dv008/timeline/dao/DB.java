package se.lnu.c1dv008.timeline.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class DB {

	private static SessionFactory sessionFactory;
	private static Session session;

	private static EventDAO eventDao;
	private static TimelineDAO timelineDao;
	private static EventWithoutDurationDAO eventWithoutDurationDao;

	static {
		try {
			Configuration configuration = new Configuration().configure();
			StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
			builder.applySettings(configuration.getProperties());
			sessionFactory = configuration.buildSessionFactory(builder.build());
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	private static Session getSession() {
		if (session == null)
			session = sessionFactory.openSession();

		return session;
	}

	public static EventDAO events() {
		if (eventDao == null)
			eventDao = new EventDAO(getSession());

		return eventDao;
	}

	public static EventWithoutDurationDAO eventsWithoutDuration() {
		if (eventWithoutDurationDao == null)
			eventWithoutDurationDao = new EventWithoutDurationDAO(getSession());

		return eventWithoutDurationDao;
	}

	public static TimelineDAO timelines() {
		if (timelineDao == null)
			timelineDao = new TimelineDAO(getSession());

		return timelineDao;
	}

	public static void closeSessionFactory() {
		sessionFactory.close();
	}
}
