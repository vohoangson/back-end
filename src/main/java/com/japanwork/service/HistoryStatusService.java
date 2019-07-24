package com.japanwork.service;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.model.RequestTranslation;
import com.japanwork.model.Translator;
import com.japanwork.model.Candidate;
import com.japanwork.model.HistoryStatus;
import com.japanwork.payload.response.RequestTranslationStatusResponse;
import com.japanwork.repository.request_translation.HistoryStatusRepository;

@Service
public class HistoryStatusService {
	@Autowired
	private HistoryStatusRepository historyStatusRepository;
	
	public HistoryStatus save(RequestTranslation requestTranslation, Timestamp timestamp, String status, String type, 
			Translator translator, Candidate candidate) {
		HistoryStatus requestTranslationStatus = new HistoryStatus();
		requestTranslationStatus.setObjecttableId(requestTranslation.getId());
		requestTranslationStatus.setCreatedAt(timestamp);
		requestTranslationStatus.setStatus(status);
		requestTranslationStatus.setType(type);
		requestTranslationStatus.setTranslator(translator);
		requestTranslationStatus.setCandidate(candidate);
		return historyStatusRepository.save(requestTranslationStatus);
	}
	
	public HistoryStatus save(RequestTranslation requestTranslation, Timestamp timestamp, String status, String reason, 
			String type, UUID userCreateId, Translator translator, Candidate candidate) {
		HistoryStatus requestTranslationStatus = new HistoryStatus();
		requestTranslationStatus.setObjecttableId(requestTranslation.getId());
		requestTranslationStatus.setUserCreateId(userCreateId);
		requestTranslationStatus.setCreatedAt(timestamp);
		requestTranslationStatus.setStatus(status);
		requestTranslationStatus.setType(type);
		requestTranslationStatus.setReason(reason);
		requestTranslationStatus.setTranslator(translator);
		requestTranslationStatus.setCandidate(candidate);
		return historyStatusRepository.save(requestTranslationStatus);
	}
	
	public HistoryStatus statusRequestTranslation(RequestTranslation requestTranslation) {
		return historyStatusRepository.findByObjecttableIdOrderByCreatedAtDesc(requestTranslation.getId()).get(0);
	}
	
	public RequestTranslationStatusResponse convertRequestTranslationStatusResponse(HistoryStatus requestTranslationStatus) {
		RequestTranslationStatusResponse obj = new RequestTranslationStatusResponse();
		obj.setId(requestTranslationStatus.getId());
		obj.setUserCreateId(requestTranslationStatus.getUserCreateId());
		obj.setStatus(requestTranslationStatus.getStatus());
		obj.setReason(requestTranslationStatus.getReason());
		obj.setCreatedAt(requestTranslationStatus.getCreatedAt());
		return obj;
	}
}
