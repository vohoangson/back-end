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

@Service
public class MessageService {

	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
    private FileHandlerService fileHandlerService;
	
	@Autowired
	private FileRepository fileRepository;
	
	@Transactional
	public Message sendMessage(User user, Conversation conversation,
			MessageRequest messageRequest) throws ForbiddenException{
		String content = messageRequest.getContent();
		String type = messageRequest.getType();
		UUID objectableId = messageRequest.getObjectableId();
		
		this.addNotification(user, conversation, content, type, objectableId);
		return create(user, content, conversation);
	}
	
	@Transactional
	public MessageResponse uploadFile(User user, Conversation conversation, MultipartFile multipartFile, String type, UUID objectableId) 
			throws ForbiddenException{
		String content = "You received new file(s)";
		
		this.addNotification(user, conversation, content, type, objectableId);
		
		Message message = create(user, content, conversation);
		
		File file = new File();
		file.setMessage(message);
		file.setName(multipartFile.getOriginalFilename());
		file.setUrl(fileHandlerService.uploadFileInMessage(multipartFile));
		file.setCreatedAt(CommonFunction.getCurrentDateTime());
		file.setUpdatedAt(CommonFunction.getCurrentDateTime());
		File fileResult = fileRepository.save(file);
		
		MessageResponse messageResponse = this.convertMassageResponse(message, fileResult);
		return messageResponse;
	}
	
	public Message create(User user, String content, Conversation conversation) {
		Message message = new Message();
		message.setContent(content);
		message.setSender(user);
		message.setConversation(conversation);
		message.setCreatedAt(CommonFunction.getCurrentDateTime());
		Message messageResult = messageRepository.save(message);
		return messageResult;
	}
	
	
	public void addNotification(User user, Conversation conversation, String content, String type, UUID objectableId) {
		String role = user.getRole();
		UUID senderId = user.getId();
		
		if(role.equals(CommonConstant.Role.CANDIDATE)) {
			if(!conversation.getCandidate().getUser().equals(user)) {
				throw new ForbiddenException(MessageConstant.FORBIDDEN_ERROR);
			}
			
			if(conversation.getCompany() != null) {
				notificationService.addNotification(senderId, conversation.getId(), objectableId, conversation.getCompany().getUser().getId(), 
						content, type);
			}
			
			if(conversation.getTranslator() != null) {
				notificationService.addNotification(senderId, conversation.getId(), objectableId, conversation.getTranslator().getUser().getId(), 
						content, type);
			}
		}

		if(role.equals(CommonConstant.Role.COMPANY)) {
			if(!conversation.getCompany().getUser().equals(user)) {
				throw new ForbiddenException(MessageConstant.FORBIDDEN_ERROR);
			}
			if(conversation.getCandidate() != null) {
				notificationService.addNotification(senderId, conversation.getId(), objectableId, conversation.getCandidate().getUser().getId(), 
						content, type);
			}
			
			if(conversation.getTranslator() != null) {
				notificationService.addNotification(senderId, conversation.getId(), objectableId, conversation.getTranslator().getUser().getId(),
						content, type);
			}
		}

		if(role.equals(CommonConstant.Role.TRANSLATOR)) {
			if(!conversation.getTranslator().getUser().equals(user)) {
				throw new ForbiddenException(MessageConstant.FORBIDDEN_ERROR);
			}
			
			if(conversation.getCompany() != null) {
				notificationService.addNotification(senderId, conversation.getId(), objectableId, conversation.getCompany().getUser().getId(), 
						content, type);
			}
			
			if(conversation.getCandidate() != null) {
				notificationService.addNotification(senderId, conversation.getId(), objectableId, conversation.getCandidate().getUser().getId(), 
						content, type);
			}
		}
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
	
	public MessageResponse convertMassageResponse(Message message, File file) {
		MessageResponse messageResponse = new MessageResponse();
		messageResponse.setId(message.getId());
		messageResponse.setSenderId(message.getSender().getId());
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
		messageResponse.setSenderId(message.getSender().getId());
		messageResponse.setConversationId(message.getConversation().getId());
		messageResponse.setContent(message.getContent());
		messageResponse.setCreateAt(message.getCreatedAt());
		messageResponse.setRead(message.isRead());
		messageResponse.setFile(message.getFile());
		return messageResponse;
	}
}
