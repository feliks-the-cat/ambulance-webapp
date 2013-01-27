package com.ambulance.core.service;

import java.util.List;

import com.ambulance.core.domain.Visit;

public interface VisitService {
	void 			saveOrUpdate(Visit visit);
	List<Visit> 	getVisitList();
	boolean 		sendToAnalysis(Visit visit);
	void	 		closeVisit(Visit visit);
}