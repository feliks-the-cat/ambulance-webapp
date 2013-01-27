package com.ambulance.core.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ambulance.core.dao.DoctorRepository;
import com.ambulance.core.domain.Doctor;
import com.ambulance.core.service.DoctorService;

@Service("doctorService")
@Transactional
public class DoctorServiceImpl implements DoctorService{

	@Inject
	private DoctorRepository doctorRepository;
	
	@Override
	@Transactional
	public void saveOrUpdate(Doctor doctor) {
		doctorRepository.saveOrUpdate(doctor);
	}

	@Override
	@Transactional
	public List<Doctor> getDoctorList() {
		return doctorRepository.findAll();
	}

	@Override
	@Transactional
	public Doctor getDoctorById(Long id) {
		return doctorRepository.getById(id);
	}

	@Override
	@Transactional
	public boolean existDoctor(Long id) {
		return doctorRepository.exist(id);
	}

	@Override
	@Transactional
	public Doctor getDoctorByLogin(String login) {
		return doctorRepository.getDoctorByLogin(login);
	}
}