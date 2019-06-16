package com.japanwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.service.NotificationService;

@Controller
public class NotificationController {

	@Autowired
	private NotificationService notificationService;
	
	public BaseDataResponse create() {
		return notificationService.save();
	}
}
