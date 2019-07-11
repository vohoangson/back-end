package com.japanwork.repository.favorite;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Candidate;
import com.japanwork.model.Favorite;
import com.japanwork.model.Job;

public interface FavoriteRepository extends JpaRepository<Favorite, UUID>{
	public Favorite findByJobAndCandidateAndFavoriteTypeAndIsDelete(Job job, Candidate candidate, String favoriteType, boolean isDel);
}
