package com.ambulance.core.dao;

import org.springframework.stereotype.Repository;

import com.ambulance.core.domain.Visit;

@Repository("visitRepository")
public class VisitRepository extends GenericDAOImpl<Visit>{

	public VisitRepository() {
		super(Visit.class);
	}
}