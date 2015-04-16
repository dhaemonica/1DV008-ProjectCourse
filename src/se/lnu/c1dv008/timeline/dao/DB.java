package se.lnu.c1dv008.timeline.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import se.lnu.c1dv008.timeline.model.TestString;

public class DB {

	private static SessionFactory sessionFactory;
	private static Session session;

	private static TestStringDao testStringDao;

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

	public static Session getSession() {
		if (session == null)
			session = sessionFactory.openSession();

		return session;
	}

	public static void events() {

	}

	public static TestStringDao testStrings() {
		if (testStringDao == null)
			testStringDao = new TestStringDao();

		testStringDao.save(new TestString("ololo"));
		testStringDao.save(new TestString("ololo2"));

		return testStringDao;
	}

	public static void timelines() {

	}
}
