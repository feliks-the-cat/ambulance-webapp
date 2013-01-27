package com.ambulance.core.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ambulance.core.domain.Doctor;

@Repository("doctorRepository")
public class DoctorRepository extends GenericDAOImpl<Doctor> {

	public DoctorRepository() {
		super(Doctor.class);
	}
	
	public Doctor getDoctorByLogin(String login){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Doctor.class)
				.add(Restrictions.eq("login", login));
		
		@SuppressWarnings("unchecked")
		List<Doctor> searchResult = criteria.list();
		
		Doctor result = null;
		if (searchResult != null)
			result = searchResult.get(0);
		return result;
	}
}