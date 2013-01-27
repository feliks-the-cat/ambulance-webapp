package com.ambulance.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "analysis")
public class Analysis implements Serializable {

	private static final long serialVersionUID = -5725282901289254381L;

	@Id
	@GeneratedValue(generator = "gen")
    @GenericGenerator(name = "gen", strategy = "foreign", parameters = @Parameter(name = "property", value = "visit"))
	@Column(name = "analysis_id")
	private Long id;
	
	@Column(name = "analysis_wbc") //white blood cells Ч белые кров€ные тельца
	private String wbc;
	
	@Column(name = "analysis_rbc") //red blood cells Ч красные кров€ные тельца
	private String rbc;
	
	@Column(name = "analysis_hgb") //hemoglobin - гемоглобин
	private String hgb;
	
	@Column(name = "analysis_esr") //erythrocyte sedimentation rate - скорость оседани€ эритроцитов
	private String esr;

	@Column(name = "analysis_noted")
	private Boolean noted;
	
	@OneToOne
    @PrimaryKeyJoinColumn
	private Visit visit;
	
	public Analysis() {
		noted = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWbc() {
		return wbc;
	}

	public void setWbc(String wbc) {
		this.wbc = wbc;
	}

	public String getRbc() {
		return rbc;
	}

	public void setRbc(String rbc) {
		this.rbc = rbc;
	}

	public String getHgb() {
		return hgb;
	}

	public void setHgb(String hgb) {
		this.hgb = hgb;
	}

	public String getEsr() {
		return esr;
	}

	public void setEsr(String esr) {
		this.esr = esr;
	}

	public Boolean getNoted() {
		return noted;
	}

	public void setNoted(Boolean noted) {
		this.noted = noted;
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}
}