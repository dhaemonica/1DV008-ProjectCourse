package se.lnu.c1dv008.timeline.dao;

import java.util.List;

import org.hibernate.Transaction;

import se.lnu.c1dv008.timeline.model.TestString;

public class TestStringDao implements DAO<TestString> {

	@Override
	public void save(TestString entity) {
		Transaction transaction = DB.getSession().beginTransaction();
		DB.getSession().save(entity);
		transaction.commit();
	}

	@Override
	public void update(TestString entity) {
		Transaction transaction = DB.getSession().beginTransaction();
		DB.getSession().update(entity);
		transaction.commit();
	}

	@Override
	public void delete(TestString entity) {
		Transaction transaction = DB.getSession().beginTransaction();
		DB.getSession().delete(entity);
		transaction.commit();
	}

	@Override
	public TestString findById(String id) {
		return (TestString) DB.getSession().get(TestString.class, id);
	}

	@Override
	public List<TestString> findAll() {
		return DB.getSession().createCriteria(TestString.class).list();
	}

	@Override
	public void deleteAll() {
		List<TestString> entityList = findAll();
		for (TestString entity : entityList) {
			delete(entity);
		}
	}
	
}
