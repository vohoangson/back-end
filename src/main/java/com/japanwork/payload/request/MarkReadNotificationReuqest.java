package com.japanwork.payload.request;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MarkReadNotificationReuqest {
	@JsonProperty("notification_ids")
	private Set<Long> notificationIds;

	public Set<Long> getNotificationIds() {
		return notificationIds;
	}

	public void setNotificationIds(Set<Long> notificationIds) {
		this.notificationIds = notificationIds;
	}
	
}
