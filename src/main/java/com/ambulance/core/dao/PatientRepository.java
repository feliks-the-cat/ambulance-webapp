package com.ambulance.core.dao;

import org.springframework.stereotype.Repository;

import com.ambulance.core.domain.Patient;

@Repository("patientRepository")
public class PatientRepository extends GenericDAOImpl<Patient> {

	public PatientRepository() {
		super(Patient.class);
	}
}