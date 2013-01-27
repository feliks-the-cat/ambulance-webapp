package com.ambulance.core.web.beans;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.ambulance.core.domain.Doctor;
import com.ambulance.core.domain.Patient;
import com.ambulance.core.service.DoctorService;
import com.ambulance.core.service.PatientService;

@Named
@Scope("session")
public final class PatientBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private PatientService patientService;
	
	@Inject
	private DoctorService doctorService;

	private List<Patient> patients;
	private List<Patient> filteredPatients;
	private SelectItem[]  sexOptions; 
	private Patient 	  selectedPatient;
	private Doctor 		  doctor;
	private boolean 	  editable = true;
	private String 		  update;
	
	private CartesianChartModel cartesianModel;
	
	public PatientBean() {
		createSexOptions();
	}
	
	@PostConstruct
	private void init(){
		populateTable();
		getLoggedUser();
		createCartesianModel();
	}
	
	private void populateTable(){
		setPatients(patientService.getPatientList());
	}
	
	public void updatePatientView(ComponentSystemEvent event){
		populateTable();
	}
	
	private void getLoggedUser(){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		setDoctor(doctorService.getDoctorByLogin(user.getUsername()));
	}

	private void createSexOptions() {
		sexOptions = new SelectItem[3];
		sexOptions[0] = new SelectItem("", "-"); 
		sexOptions[1] = new SelectItem("М", "М"); 
		sexOptions[2] = new SelectItem("Ж", "Ж"); 
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

	public SelectItem[] getSexOptions() {
		return sexOptions;
	}

	public void setSexOptions(SelectItem[] sexOptions) {
		this.sexOptions = sexOptions;
	}

	public List<Patient> getFilteredPatients() {
		return filteredPatients;
	}

	public void setFilteredPatients(List<Patient> filteredPatients) {
		this.filteredPatients = filteredPatients;
	}

	public Patient getSelectedPatient() {
		if (selectedPatient == null)
			selectedPatient = new Patient();
		return selectedPatient;
	}

	public void setSelectedPatient(Patient selectedPatient) {
		this.selectedPatient = selectedPatient;
	}

	public void newPatient(ActionEvent actionEvent){
		selectedPatient = new Patient();
		editable = false;
	}
	
	public void savePatient(ActionEvent actionEvent){
		if (!editable)
			selectedPatient.setId(null);
		patientService.saveOrUpdate(selectedPatient);
		editable = true;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("", "Запись сохранена"));
		populateTable();
	}
	
	public void setPatientIdToVisitBean(){
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("patient_id", selectedPatient.getId());
	}

	public String getUpdate() {
		populateTable();
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public CartesianChartModel getCartesianModel() {
		return cartesianModel;
	}

	public void createCartesianModel() {
		cartesianModel = new CartesianChartModel();  
		ChartSeries chart = new ChartSeries(); 
		chart.setLabel("Пациенты");  
		
		for (Patient patient : patients)
			if (patient.getVisitSize()>0)
				chart.set(patient.getSurname(), patient.getVisitSize());
				
        cartesianModel.addSeries(chart); 
	}
}