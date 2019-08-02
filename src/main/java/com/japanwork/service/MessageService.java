package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.japanwork.security.UserPrincipal;

@Service
public class MessageService {
	
	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private UserService userService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CandidateService candidateService;

	@Autowired
	private TranslatorService translatorService;
	
	@Autowired
	private ConversationService conversationService;
	
	@Autowired
	private NotificationService notificationService;
	
	@Transactional
	public Message addMessage(UserPrincipal userPrincipal, UUID id,
			MessageRequest messageRequest) throws ForbiddenException{
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());

		UUID senderId = null;
		User user = userService.getUser(userPrincipal);

		Conversation conversation = conversationService.findByIdAndIsDelete(id, null);
		if(user.getRole().equals(CommonConstant.Role.CANDIDATE)) {
			senderId = candidateService.myCandidate(userPrincipal).getId();
			if(!conversation.getCandidate().getId().equals(senderId)) {
				throw new ForbiddenException(MessageConstant.ERROR_403_MSG);
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
			senderId = companyService.myCompany(userPrincipal).getId();
			if(!conversation.getCompany().getId().equals(senderId)) {
				throw new ForbiddenException(MessageConstant.ERROR_403_MSG);
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
			senderId = translatorService.myTranslator(userPrincipal).getId();
			if(!conversation.getTranslator().getId().equals(senderId)) {
				throw new ForbiddenException(MessageConstant.ERROR_403_MSG);
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
		message.setConversation(conversationService.findByIdAndIsDelete(id, null));
		message.setCreatedAt(timestamp);
		message.setContent(messageRequest.getContent());		
		message.setDeletedAt(null);
		Message result = messageRepository.save(message);
		return result;
	}
	
	public Page<Message> listMessage(UUID id, int page, int paging) throws ResourceNotFoundException{
		try {
			Conversation conversation = conversationService.findByIdAndIsDelete(id, null);
			Page<Message> pages = messageRepository.findByConversationAndDeletedAt(
			        PageRequest.of(page-1, paging, Sort.by("createdAt").descending()), conversation, null);
			return pages;
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
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
