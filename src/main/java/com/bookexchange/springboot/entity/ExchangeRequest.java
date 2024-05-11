package com.bookexchange.springboot.entity;

public class ExchangeRequest {
	private Long id;
	private String requestedBook;
	private String deliveryMethod;
	private int duration; // Days/weeks
	private String status; // "Pending", "Accepted", "Declined", "Completed"

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRequestedBook() {
		return requestedBook;
	}

	public void setRequestedBook(String requestedBook) {
		this.requestedBook = requestedBook;
	}

	public String getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
