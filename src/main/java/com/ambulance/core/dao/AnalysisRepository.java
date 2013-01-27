package com.ambulance.core.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ambulance.core.domain.Analysis;
import com.ambulance.core.domain.Visit;

@Repository("analysisRepository")
public class AnalysisRepository extends GenericDAOImpl<Analysis>{

	public AnalysisRepository() {   
		super(Analysis.class);
	}
	
	public List<Analysis> getNotNotedAnalysisList(){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Analysis.class)
				.add(Restrictions.eq("noted", false));
		
		@SuppressWarnings("unchecked")
		List<Analysis> searchResult = criteria.list();
		return searchResult;
	}

	public List<Analysis> findAnalysisByVisit(Visit visit) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Analysis.class)
				.add(Restrictions.eq("id", visit.getId()));
		
		@SuppressWarnings("unchecked")
		List<Analysis> searchResult = criteria.list();
		return searchResult;
	}
}