package com.japanwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.model.User;
import com.japanwork.payload.request.MarkReadNotificationRequest;
import com.japanwork.payload.response.UnreadsNumberNotificationResponse;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.NotificationService;
import com.japanwork.support.CommonSupport;

@Controller
public class NotificationController {	
	@Autowired
	private NotificationService notificationService;

	@Autowired
	private CommonSupport commonSupport;
	
	@GetMapping(UrlConstant.URL_NOTIFICATIONS)
	@ResponseBody
	public ResponseDataAPI index(@RequestParam(defaultValue = "1", name = "page") int page,
			@RequestParam(defaultValue = "25", name = "paging") int paging,
			@CurrentUser UserPrincipal userPrincipal) {
		User user = commonSupport.loadUserById(userPrincipal.getId());
		return notificationService.index(user, page, paging);
	} 
	
	@GetMapping(UrlConstant.URL_NOTIFICATIONS_UNREADS_NUMBER)
	@ResponseBody
	public ResponseDataAPI countNotificationUnread( @CurrentUser UserPrincipal userPrincipal) {
		User user = commonSupport.loadUserById(userPrincipal.getId());
		int num = notificationService.countNotificationUnread(user);
		UnreadsNumberNotificationResponse obj = new UnreadsNumberNotificationResponse(num);
		return new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS, obj, null, null);
	} 
	
	@PatchMapping(UrlConstant.URL_NOTIFICATIONS_MARK_ALL_READ)
	@ResponseBody
	public ResponseDataAPI updateAllRead( @CurrentUser UserPrincipal userPrincipal) {
		User user = commonSupport.loadUserById(userPrincipal.getId());
		notificationService.updateAllRead(user);
		return new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS, null, null, null);
	} 
	
	@PatchMapping(UrlConstant.URL_NOTIFICATIONS_MARK_READS)
	@ResponseBody
	public ResponseDataAPI update( @CurrentUser UserPrincipal userPrincipal, 
			@RequestBody MarkReadNotificationRequest markReadNotificationRequest) {
		notificationService.update(userPrincipal, markReadNotificationRequest);
		return new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS, null, null, null);
	}
}
