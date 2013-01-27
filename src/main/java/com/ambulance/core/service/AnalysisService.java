package com.ambulance.core.service;

import java.util.List;

import com.ambulance.core.domain.Analysis;

public interface AnalysisService {
	void 			saveOrUpdate(Analysis analysis);
	List<Analysis> 	getAnalysisList();
}