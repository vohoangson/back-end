package com.japanwork.payload.response;

import java.util.UUID;

public class ConversationResponse {
	private UUID id;
	private CompanyResponse company;
	private CandidateResponse candidate;
	private TranslatorResponse translator;
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public CompanyResponse getCompany() {
		return company;
	}
	public void setCompany(CompanyResponse company) {
		this.company = company;
	}
	public CandidateResponse getCandidate() {
		return candidate;
	}
	public void setCandidate(CandidateResponse candidate) {
		this.candidate = candidate;
	}
	public TranslatorResponse getTranslator() {
		return translator;
	}
	public void setTranslator(TranslatorResponse translator) {
		this.translator = translator;
	}
	public ConversationResponse(UUID id, CompanyResponse company, CandidateResponse candidate,
			TranslatorResponse translator) {
		super();
		this.id = id;
		this.company = company;
		this.candidate = candidate;
		this.translator = translator;
	}
}
