package com.japanwork.repository.favorite;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, UUID>{

}
