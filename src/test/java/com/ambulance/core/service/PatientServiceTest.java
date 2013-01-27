package com.ambulance.core.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ambulance.core.domain.Patient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/datasource-tx-jpa.xml"})
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class PatientServiceTest {
	
	@SuppressWarnings("unused")
	private static final 	Log 	 logger = LogFactory.getLog(PatientServiceTest.class);
	private 			 	Patient patient;
	
	@Inject
	private PatientService patientService;
	
	// Инициализация перед каждым тестом
	@Before
	public void initPatient(){
		patient = new Patient();
		patient.setName("patient_name");
		patient.setSurname("patient_surname");
	}

	@Test
	public void testGetPatientList(){
		List<Patient> patientList = patientService.getPatientList();
		
		assertNotNull(patientList);
		assertEquals(1, patientList.size());
		logger.info("Done testGetPatientList()");
	}
	
	@Test
	public void testSavePatient() {
		Integer beforeSize = patientService.getPatientList().size();
		patientService.saveOrUpdate(patient);
		Integer afterSize = patientService.getPatientList().size()-1;

		assertEquals(beforeSize, afterSize);
		logger.info("Done testSavePatient()");
	}

	 
	@Test
	public void testDeletePatient(){
		Integer beforeSize = patientService.getPatientList().size();
		patientService.saveOrUpdate(patient);
		patientService.deletePatient(patient);
		Integer afterSize = patientService.getPatientList().size();
		
		assertEquals(beforeSize, afterSize);
		logger.info("Done testDeletePatient()");
	}
	
	@Test
	public void testGetPatientById(){
		Patient patient = patientService.getPatientById(new Long(1));
		assertNotNull(patient);
		logger.info(patient.toString());
	}
	
	@Test
	public void testExistPatient(){
		boolean patientExist 	= patientService.existPatient(new Long(1));
		boolean patientNotExist = patientService.existPatient(new Long(2));
		
		assertTrue(patientExist);
		assertFalse(patientNotExist);
	}
}