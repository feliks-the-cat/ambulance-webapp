package com.ambulance.core.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "visits")
public class Visit implements Serializable {
	
	private static final long serialVersionUID = -8831339227353164157L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "visit_id")
	private Long id;
	
	@Column(name = "visit_date")
	private String date;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "visit_patient_id", insertable = true, updatable = false, 
            	nullable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private Patient patient;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "visit_doctor_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private Doctor doctor;
	
	@Column(name = "visit_diagnosis") // диагноз
	private String diagnosis;
	
	@Column(name = "visit_complaints") // жалобы
	private String complaints;
	
	@Column(name = "visit_status") // статус
	private String status;
	
	@Column(name = "visit_treatment") // лечение
	private String treatment;
	
	@Column(name = "visit_closed") 
	private Boolean closed;
	
	@Column(name = "visit_closed_date")
	private String closed_date;
	
	@OneToOne(mappedBy = "visit", cascade = CascadeType.ALL)
	private Analysis analysis;
	
	public Visit(){
		closed = false;
		if (date == null){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
			setDate(dateFormat.format(new Date()));
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getComplaints() {
		return complaints;
	}

	public void setComplaints(String complaints) {
		this.complaints = complaints;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}
	
	public Analysis getAnalysis() {
		return analysis;
	}

	public void setAnalysis(Analysis analysis) {
		this.analysis = analysis;
	}

	public Boolean isClosed() {
		return closed;
	}

	public void setClosed(Boolean closed) {
		this.closed = closed;
	}

	public String getClosed_date() {
		return closed_date;
	}

	public void setClosed_date(String closed_date) {
		this.closed_date = closed_date;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((complaints == null) ? 0 : complaints.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((diagnosis == null) ? 0 : diagnosis.hashCode());
		result = prime * result + ((doctor == null) ? 0 : doctor.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((patient == null) ? 0 : patient.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((treatment == null) ? 0 : treatment.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Visit other = (Visit) obj;
		if (complaints == null) {
			if (other.complaints != null)
				return false;
		} else if (!complaints.equals(other.complaints))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (diagnosis == null) {
			if (other.diagnosis != null)
				return false;
		} else if (!diagnosis.equals(other.diagnosis))
			return false;
		if (doctor == null) {
			if (other.doctor != null)
				return false;
		} else if (!doctor.equals(other.doctor))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (patient == null) {
			if (other.patient != null)
				return false;
		} else if (!patient.equals(other.patient))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (treatment == null) {
			if (other.treatment != null)
				return false;
		} else if (!treatment.equals(other.treatment))
			return false;
		return true;
	}
}