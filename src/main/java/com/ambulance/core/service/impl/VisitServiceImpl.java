package com.ambulance.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ambulance.core.dao.AnalysisRepository;
import com.ambulance.core.dao.VisitRepository;
import com.ambulance.core.domain.Analysis;
import com.ambulance.core.domain.Visit;
import com.ambulance.core.service.VisitService;

@Service("visitService")
@Transactional
public class VisitServiceImpl implements VisitService {

	@Inject
	private VisitRepository visitRepository;
	
	@Inject
	private AnalysisRepository analysisRepository;
	
	@Override
	@Transactional
	public void saveOrUpdate(Visit visit) {
		visitRepository.saveOrUpdate(visit);
	}

	@Override
	@Transactional
	public List<Visit> getVisitList() {
		return visitRepository.findAll();
	}

	@Override
	@Transactional
	public boolean sendToAnalysis(Visit visit) {
		boolean result;
		if (analysisRepository.findAnalysisByVisit(visit).size() > 0)
			result = false;
		
		else
		{
			Analysis analysis = new Analysis();
			visit.setAnalysis(analysis);
			analysis.setVisit(visit);
			visitRepository.saveOrUpdate(visit);
			result = true;
		}
		return result;
	}

	@Override
	@Transactional
	public void closeVisit(Visit visit) {
		visit.setClosed(true);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		String closed_date = dateFormat.format(new Date());
		
		visit.setClosed_date(closed_date);
		visitRepository.saveOrUpdate(visit);
	}
}