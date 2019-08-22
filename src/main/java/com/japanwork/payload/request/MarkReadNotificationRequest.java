package com.japanwork.payload.request;

import java.util.Set;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MarkReadNotificationRequest {
	@NotNull
	@JsonProperty("notification_ids")
	private Set<Long> notificationIds;

	public Set<Long> getNotificationIds() {
		return notificationIds;
	}

	public void setNotificationIds(Set<Long> notificationIds) {
		this.notificationIds = notificationIds;
	}
	
}
