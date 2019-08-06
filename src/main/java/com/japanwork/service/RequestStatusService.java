package com.japanwork.service;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.model.RequestStatus;
import com.japanwork.model.RequestTranslation;
import com.japanwork.payload.response.RequestTranslationStatusResponse;
import com.japanwork.repository.request_status.RequestStatusRepository;

@Service
public class RequestStatusService {
	@Autowired
	private RequestStatusRepository requestStatusRepository;
	
	public RequestStatus save(RequestTranslation requestTranslation, Timestamp timestamp, String status) {
		RequestStatus requestTranslationStatus = new RequestStatus();
		requestTranslationStatus.setRequestTranslation(requestTranslation);
		requestTranslationStatus.setCreatedAt(timestamp);
		requestTranslationStatus.setStatus(status);
		requestTranslationStatus.setTranslator(requestTranslation.getTranslator());
		return requestStatusRepository.save(requestTranslationStatus);
	}
	
	public RequestStatus save(RequestTranslation requestTranslation, Timestamp timestamp, String status, String reason, UUID creatorId) {
		RequestStatus requestTranslationStatus = new RequestStatus();
		requestTranslationStatus.setRequestTranslation(requestTranslation);
		requestTranslationStatus.setCreatorId(creatorId);
		requestTranslationStatus.setCreatedAt(timestamp);
		requestTranslationStatus.setStatus(status);
		requestTranslationStatus.setReason(reason);
		requestTranslationStatus.setTranslator(requestTranslation.getTranslator());
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
