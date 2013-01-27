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

import com.ambulance.core.domain.Analysis;
import com.ambulance.core.domain.Doctor;
import com.ambulance.core.service.AnalysisService;
import com.ambulance.core.service.DoctorService;

@Named
@Scope("session")
public final class AnalysisBean implements Serializable{

	private static final long serialVersionUID = -2278778974968398276L;
	
	@Inject
	private DoctorService doctorService;
	
	@Inject
	private AnalysisService analysisService;
	
	private List<Analysis> analysis;
	private List<Analysis> filteredAnalysis;
	private Analysis 	   selectedAnalysis;
	private Doctor 		   doctor;
	public AnalysisBean() {
	}
	
	@PostConstruct
	private void init(){
		populateTable();
		getLoggedUser();
	}
	
	private void populateTable(){
		setAnalysis(analysisService.getAnalysisList());
	}
	
	private void getLoggedUser(){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		setDoctor(doctorService.getDoctorByLogin(user.getUsername()));
	}

	public List<Analysis> getAnalysis() {
		return analysis;
	}

	public void setAnalysis(List<Analysis> analysis) {
		this.analysis = analysis;
	}

	public List<Analysis> getFilteredAnalysis() {
		return filteredAnalysis;
	}

	public void setFilteredAnalysis(List<Analysis> filteredAnalysis) {
		this.filteredAnalysis = filteredAnalysis;
	}

	public Analysis getSelectedAnalysis() {
		if (selectedAnalysis == null)
			selectedAnalysis = new Analysis();
		return selectedAnalysis;
	}

	public void setSelectedAnalysis(Analysis selectedAnalysis) {
		this.selectedAnalysis = selectedAnalysis;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	public void saveAnalysis(ActionEvent actionEvent){
		selectedAnalysis.setNoted(true);
		analysisService.saveOrUpdate(selectedAnalysis);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("", "Запись сохранена"));
		populateTable();
	}
}