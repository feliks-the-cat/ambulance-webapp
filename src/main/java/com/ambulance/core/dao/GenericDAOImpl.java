package com.ambulance.core.dao;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.SessionFactory;

public class GenericDAOImpl<T>  implements GenericDAO<T>{
  
	final Class<T> clazz;
	
	@Inject
	protected SessionFactory sessionFactory;

	public GenericDAOImpl(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public void saveOrUpdate(T entity) {
		sessionFactory.getCurrentSession().saveOrUpdate(entity);
		sessionFactory.getCurrentSession().flush();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		String entityName = clazz.getName();
		entityName = entityName.substring(entityName.lastIndexOf(".")+1);
		
		return sessionFactory.getCurrentSession().createQuery("from "+ entityName + " e").list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getById(Long id) {
		return (T) sessionFactory.getCurrentSession().get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean exist(Long id) {
		T temp = (T) sessionFactory.getCurrentSession().get(clazz, id);
		return temp != null;
	}
}