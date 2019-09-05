package com.japanwork.service;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.common.CommonFunction;
import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ServerError;
import com.japanwork.model.Candidate;
import com.japanwork.model.Favorite;
import com.japanwork.model.Job;
import com.japanwork.model.JobTranslation;
import com.japanwork.model.Language;
import com.japanwork.model.User;
import com.japanwork.repository.favorite.FavoriteRepository;
import com.japanwork.security.UserPrincipal;
import com.japanwork.support.CommonSupport;

@Service
public class FavoriteService {
	@Autowired
	private FavoriteRepository favoriteRepository;

	@Autowired
	private CommonSupport commonSupport;

	@PersistenceContext
	private EntityManager entityManager;

	public Favorite destroy(Job job, UserPrincipal userPrincipal) {
		try {
			Candidate candidate = commonSupport.loadCandidateByUser(userPrincipal.getId());

			Favorite favorite = favoriteRepository.findByJobAndCandidateAndFavoriteTypeAndDeletedAt(job, candidate, CommonConstant.FavoriteType.CANDIDATE_JOB, null);
			favorite.setDeletedAt(CommonFunction.getCurrentDateTime());

			Favorite result = favoriteRepository.save(favorite);
			return result;
		} catch (Exception e) {
			throw new ServerError(MessageConstant.CANDIDATE_UNFAVORITE_JOB_FAIL);
		}
	}

	public Favorite create(Job job, User user) throws ServerError{
		try {
			Job obj = findFavoriteJob(user, job.getId());
			if(obj == null) {Favorite favorite = new Favorite();

				favorite.setCandidate(commonSupport.loadCandidateByUser(user.getId()));
				favorite.setJob(job);
				favorite.setFavoriteType(CommonConstant.FavoriteType.CANDIDATE_JOB);
				favorite.setCreatedAt(CommonFunction.getCurrentDateTime());
				favorite.setDeletedAt(null);

				Favorite result = favoriteRepository.save(favorite);
				return result;
			} else {
				throw new ServerError(MessageConstant.CANDIDATE_FAVORITE_JOB_FAIL);
			}
		} catch (Exception e) {
			throw new ServerError(MessageConstant.CANDIDATE_FAVORITE_JOB_FAIL);
		}
	}

	public List<JobTranslation> index(User user, Language language) {

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT DISTINCT jt ");
		sql.append("    FROM JobTranslation jt ");
		sql.append("	INNER JOIN Job j ");
		sql.append("		ON jt.job.id = j.id ");
		sql.append("		AND jt.language.id = '" + language.getId() + "' ");
		sql.append("	INNER JOIN Favorite fa ");
		sql.append("	ON fa.job.id = j.id ");
		sql.append("	WHERE ");
		sql.append("	j.deletedAt is null ");
		sql.append("	AND fa.deletedAt is null ");
		if(user.getRole().equals(CommonConstant.Role.CANDIDATE)) {
			Candidate candidate = commonSupport.loadCandidateByUser(user.getId());
			sql.append("	AND fa.candidate.id = '" + candidate.getId() + "' ");
			sql.append("	AND fa.favoriteType = '" + CommonConstant.FavoriteType.CANDIDATE_JOB + "' ");
		}
		List<JobTranslation> list = (List<JobTranslation>)entityManager.createQuery(sql.toString(), JobTranslation.class).getResultList();
		return list;
	}

	public Job findFavoriteJob(User user, UUID id) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT DISTINCT j ");
		sql.append("    FROM Job j ");
		sql.append("	INNER JOIN Favorite fa ");
		sql.append("	ON fa.job.id = j.id ");
		sql.append("	WHERE ");
		sql.append("	j.deletedAt is null ");
		sql.append("	AND j.id = '" + id + "' ");
		sql.append("	AND fa.deletedAt is null ");
		if(user.getRole().equals(CommonConstant.Role.CANDIDATE)) {
			Candidate candidate = commonSupport.loadCandidateByUser(user.getId());
			sql.append("	AND fa.candidate.id = '" + candidate.getId() + "' ");
			sql.append("	AND fa.favoriteType = '" + CommonConstant.FavoriteType.CANDIDATE_JOB + "' ");
		}
		List<Job> list = (List<Job>)entityManager.createQuery(sql.toString(), Job.class).getResultList();
		if(list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
