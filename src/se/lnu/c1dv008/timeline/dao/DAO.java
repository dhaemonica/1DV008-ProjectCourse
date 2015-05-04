package se.lnu.c1dv008.timeline.dao;

import java.util.List;

public interface DAO<T> {

	public void save(T entity);

	public void update(T entity);

	public void delete(T entity);

	public T findById(long id);

	public List<T> findAll();

	public void deleteAll();
}
