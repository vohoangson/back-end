package com.japanwork.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PageInfo {
	@JsonProperty("current_page")
	private int currentPage;
	@JsonProperty("next_page")
	private int nextPage;
	@JsonProperty("prev_page")
	private int prevPage;
	@JsonProperty("total_pages")
	private int totalPage;
	@JsonProperty("total_count")
	private long totalCount;
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getNextPage() {
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	public int getPrevPage() {
		return prevPage;
	}
	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public PageInfo(int currentPage, int nextPage, int prevPage, int totalPage, long totalCount) {
		super();
		this.currentPage = currentPage;
		this.nextPage = nextPage;
		this.prevPage = prevPage;
		this.totalPage = totalPage;
		this.totalCount = totalCount;
	}
	
	public PageInfo(int currentPage, int totalPage, long totalCount) {
		super();
		this.currentPage = currentPage;
		if(currentPage == 1) {
			this.prevPage = currentPage;
		} else {
			this.prevPage = currentPage - 1;
		}
		
		if(currentPage == totalPage) {
			this.nextPage = totalPage;
		} else {
			this.nextPage = totalPage;
		}
		this.totalPage = totalPage;
		this.totalCount = totalCount;
	}
	public PageInfo() {
		super();
	}
}
