package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Level;
import com.japanwork.payload.request.LevelRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.LevelResponse;
import com.japanwork.repository.level.LevelRepository;

@Service
public class LevelService {
	@Autowired
	private LevelRepository levelRepository;
	
	public Level findByIdAndIsDelete(UUID id) {
		return levelRepository.findByIdAndIsDelete(id,false);
	}
	
	public BaseDataResponse save(LevelRequest levelRequest) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Level level = new Level();
		level.setNameJa(levelRequest.getNameJa());
		level.setNameVi(levelRequest.getNameVi());
		level.setDescription(levelRequest.getDescription());
		level.setCreateDate(timestamp);
		level.setUpdateDate(timestamp);
		level.setIsDelete(false);
		
		Level result = levelRepository.save(level);
		LevelResponse levelResponse = this.setLevelResponse(result);
		BaseDataResponse response = new BaseDataResponse(levelResponse);
		
		return response;
	}
	
	public LevelResponse convertLevel(UUID id) throws ResourceNotFoundException{
		Level level = levelRepository.findByIdAndIsDelete(id, false);
		if(level == null) {
			throw new ResourceNotFoundException("Level not found for this id :: " + id);
		}
		LevelResponse levelResponse = this.setLevelResponse(level);
		
		return levelResponse;
	}
	
	private LevelResponse setLevelResponse(Level level) {
		LevelResponse levelResponse = new LevelResponse();
		levelResponse.setNameVi(level.getNameVi());
		levelResponse.setNameJa(level.getNameJa());
		levelResponse.setDescription(level.getDescription());
		
		return levelResponse;
	}
}
