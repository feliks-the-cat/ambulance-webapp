package com.ambulance.core.dao;

import org.springframework.stereotype.Repository;

import com.ambulance.core.domain.Specialty;

@Repository("specialtyRepository")
public class SpecialtyRepository extends GenericDAOImpl<Specialty> {

	public SpecialtyRepository() {
		super(Specialty.class);
	}
}