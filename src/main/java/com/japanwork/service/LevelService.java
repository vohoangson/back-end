package com.japanwork.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.common.CommonFunction;
import com.japanwork.model.Level;
import com.japanwork.payload.request.LevelRequest;
import com.japanwork.repository.level.LevelRepository;

@Service
public class LevelService {
	@Autowired
	private LevelRepository levelRepository;

	public List<Level> index() {
		List<Level> list = levelRepository.findAllByDeletedAt(null);
		return list;
	}

	public Level create(LevelRequest levelRequest) {
		Level level = new Level();
		level.setJa(levelRequest.getJa());
		level.setVi(levelRequest.getVi());
		level.setDesc(levelRequest.getDesc());
		level.setCreatedAt(CommonFunction.getCurrentDateTime());
		level.setUpdatedAt(CommonFunction.getCurrentDateTime());

		Level result = levelRepository.save(level);
		return result;
	}
}
