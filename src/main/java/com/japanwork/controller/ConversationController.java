package com.japanwork.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.UrlConstant;
import com.japanwork.model.Conversation;
import com.japanwork.model.JobApplication;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.service.ConversationService;
import com.japanwork.service.JobApplicationService;

@Controller
public class ConversationController {
	@Autowired
	private ConversationService conversationService;
	
	@Autowired
	private JobApplicationService jobApplicationService;

	@GetMapping(UrlConstant.URL_JOB_APPLICATION_ID_CONVERSATION_ALL)
	@ResponseBody
	public BaseDataResponse createConversationAll(@PathVariable UUID id) {
		JobApplication jobApplication = jobApplicationService.findByIdAndIsDelete(id);
		if(jobApplication.getAllConversation() == null) {
			Conversation conversation = conversationService.createConversationAll(jobApplication.getTranslator(), 
					jobApplication.getJob().getCompany(), jobApplication.getCandidate());
			jobApplication.setAllConversation(conversation);
			jobApplicationService.save(jobApplication);
			return new BaseDataResponse(conversationService.convertConversationResponse(conversation));
		}
		
		return new BaseDataResponse(conversationService.convertConversationResponse(jobApplication.getAllConversation()));
	}
	
	@GetMapping(UrlConstant.URL_JOB_APPLICATION_ID_CONVERSATION_CANDIDATE)
	@ResponseBody
	public BaseDataResponse createConversationSupportCandidate(@PathVariable UUID id) {
		JobApplication jobApplication = jobApplicationService.findByIdAndIsDelete(id);
		if(jobApplication.getCandidateSupportConversaion() == null) {
			Conversation conversation = conversationService.createConversationSupportCandidate(jobApplication.getTranslator(), 
					jobApplication.getCandidate());
			jobApplication.setCandidateSupportConversaion(conversation);
			jobApplicationService.save(jobApplication);
			return new BaseDataResponse(conversationService.convertConversationResponse(conversation));
		}
		return new BaseDataResponse(conversationService.convertConversationResponse(
				jobApplication.getCandidateSupportConversaion()));
		
	}
	
	@GetMapping(UrlConstant.URL_JOB_APPLICATION_ID_CONVERSATION_COMPANY)
	@ResponseBody
	public BaseDataResponse createConversationSupportCompany(@PathVariable UUID id) {
		JobApplication jobApplication = jobApplicationService.findByIdAndIsDelete(id);
		if(jobApplication.getCompanySupportConversation() == null) {
			Conversation conversation = conversationService.createConversationSupportCompany(jobApplication.getTranslator(), 
					jobApplication.getJob().getCompany());
			jobApplication.setCompanySupportConversation(conversation);
			jobApplicationService.save(jobApplication);
			return new BaseDataResponse(conversationService.convertConversationResponse(conversation));
		}
		return new BaseDataResponse(conversationService.convertConversationResponse(
				jobApplication.getCandidateSupportConversaion()));
	}
}
