package com.ambulance.core.web.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.ambulance.core.domain.Doctor;
import com.ambulance.core.domain.Patient;
import com.ambulance.core.domain.Visit;
import com.ambulance.core.service.DoctorService;
import com.ambulance.core.service.PatientService;
import com.ambulance.core.service.VisitService;

@Named
@Scope("session")
public final class VisitBean implements Serializable {
	
	private static final long serialVersionUID = 9219874070912661131L;

	@Inject
	private DoctorService doctorService;
	
	@Inject
	private VisitService visitService;
	
	@Inject
	private PatientService patientService;
	
	private List<Visit> visits;
	private List<Visit>	filteredVisits;
	private Visit 		selectedVisit;
	private Doctor 		doctor;
	private Patient		patient;
	private boolean 	editable = true;
	private String update;
	
	public VisitBean() {
		super();
	}
	
	@PostConstruct
	private void init(){
		getLoggedUser();
	}
	
	private void populateTable(){
		setPatient(patientService.getPatientById(getPatientIdFromPatientBean()));
		setVisits(patient.getVisits());
	}
	
	private void getLoggedUser(){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		setDoctor(doctorService.getDoctorByLogin(user.getUsername()));
	}

	public List<Visit> getVisits() {
		return visits;
	}

	public void setVisits(List<Visit> visits) {
		this.visits = visits;
	}

	public List<Visit> getFilteredVisits() {
		return filteredVisits;
	}

	public void setFilteredVisits(List<Visit> filteredVisits) {
		this.filteredVisits = filteredVisits;
	}

	public Visit getSelectedVisit() {
		if (selectedVisit == null)
			selectedVisit = new Visit();
		return selectedVisit;
	}

	public void setSelectedVisit(Visit selectedVisit) {
		this.selectedVisit = selectedVisit;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public void newVisit(ActionEvent actionEvent){
		selectedVisit = new Visit();
		selectedVisit.setPatient(patient);
		selectedVisit.setDoctor(doctor);
		visitService.saveOrUpdate(selectedVisit);
		
		editable = false;
	}
	
	public void saveVisit(ActionEvent actionEvent){
		if (iscloseVisit(false))
			return;
		if (!editable)
			selectedVisit.setId(null);
		
		visitService.saveOrUpdate(selectedVisit);
		
		editable = true;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("", "Запись сохранена"));
		populateTable();
	}
	
	public Long getPatientIdFromPatientBean(){
		return (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("patient_id");
		
	}
	
	public void sendToAnalysis(ActionEvent actionEvent){
		if (iscloseVisit(false))
			return;
		boolean succesfull = visitService.sendToAnalysis(selectedVisit);
		
		if (!succesfull)
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_FATAL, "", "Пациент уже был направлен на анализ"));	
		else
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Пациент был направлен на анализ"));	
	}
	
	public String getUpdate() {
		populateTable();
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}
	
	public void closeVisit(ActionEvent actionEvent){
		if (!iscloseVisit(true))
			visitService.closeVisit(selectedVisit);
	}
	public boolean iscloseVisit(boolean showInfo){
		if (selectedVisit.isClosed()){
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_FATAL, "", "Больничный уже был закрыт"));
			return true;
		}
		
		if (showInfo)
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Больничный был закрыт"));
		return false;
	}
}