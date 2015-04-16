package se.lnu.c1dv008.timeline.dao;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import se.lnu.c1dv008.timeline.model.TestString;

public class EventDAO {

	private Session currentSession;
	private Transaction currentTransaction;

	public EventDAO() {

	}

	public Session openCurrentSession() {
		currentSession = getSessionFactory().openSession();
		return currentSession;

	}

	public Session openCurrentSessionwithTransaction() {
		currentSession = getSessionFactory().openSession();
		currentTransaction = currentSession.beginTransaction();
		
		return currentSession;
	}

	public void closeCurrentSession() {
		currentSession.close();
	}


	public void closeCurrentSessionwithTransaction() {
		currentTransaction.commit();
		currentSession.close();
	}


	private static SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());

		return sessionFactory;

	}

	public Session getCurrentSession() {
		return currentSession;
	}


	public void setCurrentSession(Session currentSession) {
		this.currentSession = currentSession;
	}



	public Transaction getCurrentTransaction() {
		return currentTransaction;
	}


	public void setCurrentTransaction(Transaction currentTransaction) {
		this.currentTransaction = currentTransaction;
	}


	public void persist(TestString entity) {
		getCurrentSession().save(entity);
	}

	public void update(TestString entity) {
		getCurrentSession().update(entity);
	}

	public void delete(TestString entity) {
		getCurrentSession().delete(entity);
	}

	public TestString findById(String id) {
		TestString testString = (TestString) getCurrentSession().get(TestString.class, id);
		return testString;
	}
	
	@SuppressWarnings("unchecked")
	public List<TestString> findAll() {
		List<TestString> entities = (List<TestString>) getCurrentSession().createQuery("from TestString").list();
		return entities;
	}

	public void deleteAll() {
		List<TestString> entityList = findAll();
		for ( TestString entity : entityList) {
			delete(entity);
		}
	}

}
