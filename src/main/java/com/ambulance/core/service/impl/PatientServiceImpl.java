package com.ambulance.core.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ambulance.core.dao.PatientRepository;
import com.ambulance.core.domain.Patient;
import com.ambulance.core.service.PatientService;

@Service("patientService")
@Transactional
public class PatientServiceImpl implements PatientService {

	@Inject
	private PatientRepository patientRepository;
	
	@Override
	@Transactional
	public void saveOrUpdate(Patient patient) {
		patientRepository.saveOrUpdate(patient);
	}

	@Override
	@Transactional
	public List<Patient> getPatientList() {
		return patientRepository.findAll();
	}

	@Override
	@Transactional
	public Patient getPatientById(Long id) {
		return patientRepository.getById(id);
	}

	@Override
	@Transactional
	public boolean existPatient(Long id) {
		return patientRepository.exist(id);
	}
}