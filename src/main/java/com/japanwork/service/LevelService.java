package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Level;
import com.japanwork.payload.request.LevelRequest;
import com.japanwork.repository.level.LevelRepository;

@Service
public class LevelService {
	@Autowired
	private LevelRepository levelRepository;
	
	public List<Level> findAllByIsDelete() {
		List<Level> list = levelRepository.findAllByIsDelete(false);
		return list;
	}
	
	public Level findByIdAndIsDelete(UUID id) {
		Level level = levelRepository.findByIdAndIsDelete(id, false);
		if(level == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		return level;
	}
	
	public Level save(LevelRequest levelRequest) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Level level = new Level();
		level.setJa(levelRequest.getJa());
		level.setVi(levelRequest.getVi());
		level.setDesc(levelRequest.getDesc());
		level.setCreateDate(timestamp);
		level.setUpdateDate(timestamp);
		level.setDelete(false);
		
		Level result = levelRepository.save(level);
		
		return result;
	}
}
