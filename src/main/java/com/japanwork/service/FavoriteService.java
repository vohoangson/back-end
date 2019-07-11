package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.constant.CommonConstant;
import com.japanwork.model.Candidate;
import com.japanwork.model.Favorite;
import com.japanwork.model.Job;
import com.japanwork.model.User;
import com.japanwork.repository.favorite.FavoriteRepository;
import com.japanwork.security.UserPrincipal;

@Service
public class FavoriteService {
	@Autowired
	private FavoriteRepository favoriteRepository;
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private UserService userService;
	
	@PersistenceContext 
	private EntityManager entityManager;
	
	public String canidateUnFavoriteJob(UUID id, UserPrincipal userPrincipal) {
		try {
			Job job = jobService.findByIdAndIsDelete(id);
			Candidate candidate = candidateService.myCandidate(userPrincipal);
			
			Favorite favorite = favoriteRepository.findByJobAndCandidateAndFavoriteTypeAndIsDelete(job, candidate, CommonConstant.FavoriteType.CANDIDATE_JOB, false);
			favorite.setDelete(true);
			
			favoriteRepository.save(favorite);
			
			return "Success";
		} catch (Exception e) {
			return "Faile";
		}		
	}
	
	public String canidateFavoriteJob(UUID id, UserPrincipal userPrincipal) {
		try {
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			
			Favorite favorite = new Favorite();
			
			favorite.setCandidate(candidateService.myCandidate(userPrincipal));
			
			Job job = jobService.findByIdAndIsDelete(id);
			favorite.setJob(job);
			
			favorite.setFavoriteType(CommonConstant.FavoriteType.CANDIDATE_JOB);
			favorite.setCreateAt(timestamp);
			favorite.setDelete(false);
			
			favoriteRepository.save(favorite);
			
			return "Success";
		} catch (Exception e) {
			return "Faile";
		}		
	}
	
	public List<Job> listFavoriteJob(UserPrincipal userPrincipal) {
		User user = userService.findByIdAndIsDelete(userPrincipal.getId());
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT DISTINCT j ");
		sql.append("    FROM Job j ");
		sql.append("	INNER JOIN Favorite fa ");	
		sql.append("	ON fa.job.id = j.id ");
		sql.append("	WHERE ");
		sql.append("	j.isDelete = " + false + " ");
		sql.append("	AND fa.isDelete = " + false + " ");
		if(user.getRole().equals(CommonConstant.Role.CANDIDATE)) {
			Candidate candidate = candidateService.myCandidate(userPrincipal);
			sql.append("	AND fa.candidate.id = '" + candidate.getId() + "' ");
			sql.append("	AND fa.favoriteType = '" + CommonConstant.FavoriteType.CANDIDATE_JOB + "' ");
		}
		List<Job> list = (List<Job>)entityManager.createQuery(sql.toString(), Job.class).getResultList();
		return list;
	}
}
