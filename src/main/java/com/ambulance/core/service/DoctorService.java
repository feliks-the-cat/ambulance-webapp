package com.ambulance.core.service;

import java.util.List;

import com.ambulance.core.domain.Doctor;

public interface DoctorService {
	void 			saveOrUpdate(Doctor doctor);
	List<Doctor> 	getDoctorList();
	Doctor 			getDoctorById(Long id);
	boolean			existDoctor(Long id);
	Doctor 			getDoctorByLogin(String login);
}