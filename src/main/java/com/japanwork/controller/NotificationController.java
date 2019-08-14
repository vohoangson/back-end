package com.japanwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.UrlConstant;
import com.japanwork.payload.request.MarkReadNotificationReuqest;
import com.japanwork.payload.response.BaseDataMetaResponse;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.BaseSuccessResponse;
import com.japanwork.payload.response.UnreadsNumberNotificationResponse;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.NotificationService;

@Controller
public class NotificationController {	
	@Autowired
	private NotificationService notificationService;

	@GetMapping(UrlConstant.URL_NOTIFICATIONS)
	@ResponseBody
	public BaseDataMetaResponse notifications(@RequestParam(defaultValue = "1", name = "page") int page,
			@RequestParam(defaultValue = "25", name = "paging") int paging,
			@CurrentUser UserPrincipal userPrincipal) {
		
		return notificationService.notifications(userPrincipal, page, paging);
	} 
	
	@GetMapping(UrlConstant.URL_NOTIFICATIONS_UNREADS_NUMBER)
	@ResponseBody
	public BaseDataResponse unreadNumber( @CurrentUser UserPrincipal userPrincipal) {
		int num = notificationService.unreadNumber(userPrincipal);
		UnreadsNumberNotificationResponse obj = new UnreadsNumberNotificationResponse(num);
		return new BaseDataResponse(obj);
	} 
	
	@PatchMapping(UrlConstant.URL_NOTIFICATIONS_MARK_ALL_READ)
	@ResponseBody
	public BaseSuccessResponse markAllRead( @CurrentUser UserPrincipal userPrincipal) {
		notificationService.markAllReads(userPrincipal);
		return new BaseSuccessResponse("Success",null,null);
	} 
	
	@PatchMapping(UrlConstant.URL_NOTIFICATIONS_MARK_READS)
	@ResponseBody
	public BaseSuccessResponse markReads( @CurrentUser UserPrincipal userPrincipal, 
			@RequestBody MarkReadNotificationReuqest markReadNotificationReuqest) {
		notificationService.markReads(userPrincipal, markReadNotificationReuqest);
		return new BaseSuccessResponse("Success",null,null);
	}
}
