package com.ambulance.core.web.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.ambulance.core.domain.Doctor;
import com.ambulance.core.domain.Specialty;
import com.ambulance.core.service.DoctorService;
import com.ambulance.core.service.SpecialtyService;

@Named
@Scope("session")
public final class UserBean implements Serializable{
	
	private static final long serialVersionUID = -7185019466410960196L;

	@Inject
	private DoctorService doctorService;
	
	@Inject
	private SpecialtyService specialtyService;
	
	private List<Doctor> doctors;
	private List<Doctor> filteredDoctors;
	private Doctor 		 selectedDoctor;
	private SelectItem[] roleOptions;
	private SelectItem[] specialtyOptions;
	private Doctor 		 doctor;
	private Long 		 doctor_specialty_id;
	private boolean 	 editable = true;
	
	public UserBean() {
		super();
		createRoleOptions();
	}
	
	private void createSpecialtyOptions() {
		List<Specialty> specialtyList = specialtyService.getSpecialtyList();
		int size = specialtyList.size();
		specialtyOptions = new SelectItem[size];
		int i = 0;
		for (Specialty specialty : specialtyList)
			specialtyOptions[i++] = new SelectItem(specialty.getId(), specialty.getName()); 
	}

	@PostConstruct
	private void init(){
		createSpecialtyOptions();
		populateTable();
		getLoggedUser();
	}

	private void createRoleOptions() {
		roleOptions = new SelectItem[3];
		roleOptions[0] = new SelectItem("ROLE_DOCTOR", 		 "Врач"); 
		roleOptions[1] = new SelectItem("ROLE_LABORATORIAN", "Лаборант"); 
		roleOptions[2] = new SelectItem("ROLE_ADMIN", 		 "Администратор");
	}
	
	private void populateTable(){
		setDoctors(doctorService.getDoctorList());
	}
	
	private void getLoggedUser(){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		setDoctor(doctorService.getDoctorByLogin(user.getUsername()));
	}

	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

	public List<Doctor> getFilteredDoctors() {
		return filteredDoctors;
	}

	public void setFilteredDoctors(List<Doctor> filteredDoctors) {
		this.filteredDoctors = filteredDoctors;
	}

	public Doctor getSelectedDoctor() {
		if (selectedDoctor == null)
			selectedDoctor = new Doctor();
		return selectedDoctor;
	}

	public void setSelectedDoctor(Doctor selectedDoctor) {
		this.selectedDoctor = selectedDoctor;
	}

	public SelectItem[] getRoleOptions() {
		return roleOptions;
	}

	public void setRoleOptions(SelectItem[] roleOptions) {
		this.roleOptions = roleOptions;
	}

	public SelectItem[] getSpecialtyOptions() {
		return specialtyOptions;
	}

	public void setSpecialtyOptions(SelectItem[] specialtyOptions) {
		this.specialtyOptions = specialtyOptions;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	public void newDoctor(ActionEvent actionEvent){
		selectedDoctor = new Doctor();
		setDoctor_specialty_id(new Long(1));
		editable = false;
	}
	
	public void saveDoctor(ActionEvent actionEvent){
		if (!editable)
			selectedDoctor.setId(null);
			
		selectedDoctor.getSpecialty().setId(doctor_specialty_id);
		doctorService.saveOrUpdate(selectedDoctor);
		editable = true;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("", "Запись сохранена"));
		populateTable();
	}

	public Long getDoctor_specialty_id() {
		return doctor_specialty_id;
	}

	public void setDoctor_specialty_id(Long doctor_specialty_id) {
		this.doctor_specialty_id = doctor_specialty_id;
	}
	
	public void updateDoctorSpecialty(){
		this.doctor_specialty_id = selectedDoctor.getSpecialty().getId();
	}
}