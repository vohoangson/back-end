package com.japanwork.service;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.model.RequestStatus;
import com.japanwork.model.Translator;
import com.japanwork.payload.response.RequestTranslationStatusResponse;
import com.japanwork.repository.request_status.RequestStatusRepository;

@Service
public class RequestStatusService {
	@Autowired
	private RequestStatusRepository requestStatusRepository;
	
	public RequestStatus save(UUID requestTranslationId, Timestamp timestamp, String status, Translator translator) {
		RequestStatus requestTranslationStatus = new RequestStatus();
		requestTranslationStatus.setRequestTranslationId(requestTranslationId);
		requestTranslationStatus.setCreatedAt(timestamp);
		requestTranslationStatus.setStatus(status);
		requestTranslationStatus.setTranslator(translator);
		return requestStatusRepository.save(requestTranslationStatus);
	}
	
	public RequestStatus save(UUID requestTranslationId, Timestamp timestamp, String status, String reason, UUID creatorId, 
			Translator translator) {
		RequestStatus requestTranslationStatus = new RequestStatus();
		requestTranslationStatus.setRequestTranslationId(requestTranslationId);
		requestTranslationStatus.setCreatorId(creatorId);
		requestTranslationStatus.setCreatedAt(timestamp);
		requestTranslationStatus.setStatus(status);
		requestTranslationStatus.setReason(reason);
		requestTranslationStatus.setTranslator(translator);
		return requestStatusRepository.save(requestTranslationStatus);
	}
	
//	public HistoryStatus statusRequestTranslation(RequestTranslation requestTranslation) {
//		return historyStatusRepository.findByObjecttableIdOrderByCreatedAtDesc(requestTranslation.getId()).get(0);
//	}
	
	public RequestTranslationStatusResponse convertRequestTranslationStatusResponse(RequestStatus requestTranslationStatus) {
		RequestTranslationStatusResponse obj = new RequestTranslationStatusResponse();
		obj.setId(requestTranslationStatus.getId());
		obj.setCreatorId(requestTranslationStatus.getCreatorId());
		obj.setStatus(requestTranslationStatus.getStatus());
		obj.setReason(requestTranslationStatus.getReason());
		obj.setCreatedAt(requestTranslationStatus.getCreatedAt());
		return obj;
	}
}
