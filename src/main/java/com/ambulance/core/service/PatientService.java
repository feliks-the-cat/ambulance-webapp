package com.ambulance.core.service;

import java.util.List;

import com.ambulance.core.domain.Patient;

public interface PatientService{
	void 			saveOrUpdate(Patient patient);
	List<Patient> 	getPatientList();
	Patient 		getPatientById(Long id);
	boolean			existPatient(Long id);
}