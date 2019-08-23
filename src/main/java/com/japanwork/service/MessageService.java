package com.japanwork.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.japanwork.common.CommonFunction;
import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ForbiddenException;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Conversation;
import com.japanwork.model.File;
import com.japanwork.model.Message;
import com.japanwork.model.User;
import com.japanwork.payload.request.MessageRequest;
import com.japanwork.payload.response.MessageResponse;
import com.japanwork.repository.file.FileRepository;
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
	
	@Autowired
    private FileHandlerService fileHandlerService;
	
	@Autowired
	private FileRepository fileRepository;
	
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
        message.setContent(messageRequest.getContent());
        message.setCreatedAt(CommonFunction.getCurrentDateTime());
        message.setUpdatedAt(CommonFunction.getCurrentDateTime());
		Message result = messageRepository.save(message);
		return result;
	}
	
	@Transactional
	public MessageResponse uploadFile(User user, Conversation conversation, MultipartFile multipartFile, String type, UUID objectableId) 
			throws ForbiddenException{
		UUID senderId = null;

		if(user.getRole().equals(CommonConstant.Role.CANDIDATE)) {
			senderId = commonSupport.loadCandidateByUser(user.getId()).getId();
			if(!conversation.getCandidate().getId().equals(senderId)) {
				throw new ForbiddenException(MessageConstant.FORBIDDEN_ERROR);
			}
			if(conversation.getCompany() != null) {
				notificationService.addNotification(senderId, conversation.getId(), objectableId, conversation.getCompany().getId(), "", 
						type, user.getId());
			}
			
			if(conversation.getTranslator() != null) {
				notificationService.addNotification(senderId, conversation.getId(), objectableId, conversation.getTranslator().getId(), "", 
						type, user.getId());
			}
		}

		if(user.getRole().equals(CommonConstant.Role.COMPANY)) {
			senderId = commonSupport.loadCompanyByUser(user.getId()).getId();
			if(!conversation.getCompany().getId().equals(senderId)) {
				throw new ForbiddenException(MessageConstant.FORBIDDEN_ERROR);
			}
			if(conversation.getCandidate() != null) {
				notificationService.addNotification(senderId, conversation.getId(), objectableId, conversation.getCandidate().getId(), "", 
						type, user.getId());
			}
			
			if(conversation.getTranslator() != null) {
				notificationService.addNotification(senderId, conversation.getId(), objectableId, conversation.getTranslator().getId(), "", 
						type, user.getId());
			}
		}

		if(user.getRole().equals(CommonConstant.Role.TRANSLATOR)) {
			senderId = commonSupport.loadTranslatorByUser(user.getId()).getId();
			if(!conversation.getTranslator().getId().equals(senderId)) {
				throw new ForbiddenException(MessageConstant.FORBIDDEN_ERROR);
			}
			
			if(conversation.getCompany() != null) {
				notificationService.addNotification(senderId, conversation.getId(), objectableId, conversation.getCompany().getId(), "", 
						type, user.getId());
			}
			
			if(conversation.getCandidate() != null) {
				notificationService.addNotification(senderId, conversation.getId(), objectableId, conversation.getCandidate().getId(), "", 
						type, user.getId());
			}
		}
		
		Message message = new Message();
		message.setContent("You received new file(s)");
		message.setSenderId(senderId);
		message.setConversation(conversation);
		message.setCreatedAt(CommonFunction.getCurrentDateTime());
		Message messageResult = messageRepository.save(message);
		
		File file = new File();
		file.setMessage(message);
		file.setName(multipartFile.getOriginalFilename());
		file.setUrl(fileHandlerService.uploadFileInMessage(multipartFile));
		file.setCreatedAt(CommonFunction.getCurrentDateTime());
		file.setUpdatedAt(CommonFunction.getCurrentDateTime());
		File fileResult = fileRepository.save(file);
		
		MessageResponse messageResponse = this.convertMassageResponse(messageResult, fileResult);
		return messageResponse;
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
	
	public MessageResponse convertMassageResponse(Message message, File file) {
		MessageResponse messageResponse = new MessageResponse();
		messageResponse.setId(message.getId());
		messageResponse.setSenderId(message.getSenderId());
		messageResponse.setConversationId(message.getConversation().getId());
		messageResponse.setContent(message.getContent());
		messageResponse.setCreateAt(message.getCreatedAt());
		messageResponse.setRead(message.isRead());
		messageResponse.setFile(file);
		return messageResponse;
	}
	
	public MessageResponse convertMassageResponse(Message message) {
		MessageResponse messageResponse = new MessageResponse();
		messageResponse.setId(message.getId());
		messageResponse.setSenderId(message.getSenderId());
		messageResponse.setConversationId(message.getConversation().getId());
		messageResponse.setContent(message.getContent());
		messageResponse.setCreateAt(message.getCreatedAt());
		messageResponse.setRead(message.isRead());
		messageResponse.setFile(message.getFile());
		return messageResponse;
	}
}
