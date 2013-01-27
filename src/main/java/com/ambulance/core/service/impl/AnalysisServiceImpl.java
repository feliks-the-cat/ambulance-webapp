package com.ambulance.core.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ambulance.core.dao.AnalysisRepository;
import com.ambulance.core.domain.Analysis;
import com.ambulance.core.service.AnalysisService;

@Service("analysisService")
@Transactional
public class AnalysisServiceImpl implements AnalysisService {

	@Inject
	private AnalysisRepository analysisRepository;
	
	@Override
	@Transactional
	public void saveOrUpdate(Analysis analysis) {
		analysisRepository.saveOrUpdate(analysis);
	}

	@Override
	@Transactional
	public List<Analysis> getAnalysisList() {
		return analysisRepository.getNotNotedAnalysisList();
	}
}