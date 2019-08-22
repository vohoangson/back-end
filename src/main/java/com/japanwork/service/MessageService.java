package com.japanwork.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.japanwork.common.CommonFunction;
import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ForbiddenException;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Conversation;
import com.japanwork.model.Message;
import com.japanwork.model.User;
import com.japanwork.payload.request.MessageRequest;
import com.japanwork.payload.response.MessageResponse;
import com.japanwork.repository.message.MessageRepository;
import com.japanwork.support.CommonSupport;

@Service
public class MessageService {
	
	@Autowired
	private MessageRepository messageRepository;	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private CommonSupport commonSupport;
	
	@Transactional
	public Message create(User user, Conversation conversation,
			MessageRequest messageRequest) throws ForbiddenException{
		UUID senderId = null;

		if(user.getRole().equals(CommonConstant.Role.CANDIDATE)) {
			senderId = commonSupport.loadCandidateByUser(user.getId()).getId();
			if(!conversation.getCandidate().getId().equals(senderId)) {
				throw new ForbiddenException(MessageConstant.FORBIDDEN_ERROR);
			}
			if(conversation.getCompany() != null) {
				this.addNotification(senderId, conversation.getId(), conversation.getCompany().getId(), messageRequest, 
						conversation.getCompany().getUser().getId());
			}
			
			if(conversation.getTranslator() != null) {
				this.addNotification(senderId, conversation.getId(), conversation.getTranslator().getId(), messageRequest, 
						conversation.getTranslator().getUser().getId());
			}
		}

		if(user.getRole().equals(CommonConstant.Role.COMPANY)) {
			senderId = commonSupport.loadCompanyByUser(user.getId()).getId();
			if(!conversation.getCompany().getId().equals(senderId)) {
				throw new ForbiddenException(MessageConstant.FORBIDDEN_ERROR);
			}
			if(conversation.getCandidate() != null) {
				this.addNotification(senderId, conversation.getId(), conversation.getCandidate().getId(), messageRequest, 
						conversation.getCandidate().getUser().getId());
			}
			
			if(conversation.getTranslator() != null) {
				this.addNotification(senderId, conversation.getId(), conversation.getTranslator().getId(), messageRequest, 
						conversation.getTranslator().getUser().getId());
			}
		}

		if(user.getRole().equals(CommonConstant.Role.TRANSLATOR)) {
			senderId = commonSupport.loadTranslatorByUser(user.getId()).getId();
			if(!conversation.getTranslator().getId().equals(senderId)) {
				throw new ForbiddenException(MessageConstant.FORBIDDEN_ERROR);
			}
			
			if(conversation.getCompany() != null) {
				this.addNotification(senderId, conversation.getId(), conversation.getCompany().getId(), messageRequest, 
						conversation.getCompany().getUser().getId());
			}
			
			if(conversation.getCandidate() != null) {
				this.addNotification(senderId, conversation.getId(), conversation.getCandidate().getId(), messageRequest, 
						conversation.getCandidate().getUser().getId());
			}
		}
		
		Message message = new Message();
		message.setSenderId(senderId);
		message.setConversation(conversation);
		message.setCreatedAt(CommonFunction.dateTimeNow());
		message.setContent(messageRequest.getContent());		
		message.setDeletedAt(null);
		Message result = messageRepository.save(message);
		return result;
	}
	
	public Page<Message> index(Conversation conversation, int page, int paging) throws ResourceNotFoundException{
		try {
			Page<Message> pages = messageRepository.findByConversationAndDeletedAt(
			        PageRequest.of(page-1, paging, Sort.by("createdAt").descending()), conversation, null);
			return pages;
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException(MessageConstant.PAGE_NOT_FOUND);
		}
	}
	
	
	public void addNotification(UUID senderId, UUID conversationId, UUID receiverId, MessageRequest messageRequest, UUID userId) {
		notificationService.addNotification(senderId, conversationId, messageRequest.getObjectableId(), receiverId, messageRequest.getContent(), 
				messageRequest.getType(), userId);
	}
	
	public MessageResponse convertMassageResponse(Message message) {
		MessageResponse messageResponse = new MessageResponse();
		messageResponse.setId(message.getId());
		messageResponse.setSenderId(message.getSenderId());
		messageResponse.setConversationId(message.getConversation().getId());
		messageResponse.setContent(message.getContent());
		messageResponse.setCreateAt(message.getCreatedAt());
		messageResponse.setRead(message.isRead());
		return messageResponse;
	}
}
