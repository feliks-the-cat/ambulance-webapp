package com.ambulance.core.dao;

import java.util.List;

public interface GenericDAO<T>{
	void 		saveOrUpdate(T entity);
	List<T> 	findAll();
	T 			getById(Long id);
	boolean 	exist(Long id); 
}