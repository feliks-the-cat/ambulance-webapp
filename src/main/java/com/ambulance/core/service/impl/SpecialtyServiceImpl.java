package com.ambulance.core.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ambulance.core.dao.SpecialtyRepository;
import com.ambulance.core.domain.Specialty;
//import com.ambulance.core.domain.Specialty;
//import com.ambulance.core.service.SpecialtyService;
import com.ambulance.core.service.SpecialtyService;

@Service("specialtyService")
@Transactional
public class SpecialtyServiceImpl implements SpecialtyService {

	@Inject
	private SpecialtyRepository specialtyRepository;
	
	@Override
	@Transactional
	public List<Specialty> getSpecialtyList() {
		return specialtyRepository.findAll();
	}
}