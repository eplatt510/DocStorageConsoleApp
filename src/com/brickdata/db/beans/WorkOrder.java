package com.brickdata.db.beans;

import java.sql.Date;

public class WorkOrder {
	
	private String containers;
	private String createdBy;
	private String customerId;
	private Date dateTimeCreated;
	private Date dateTimeDelivered;
	private String deliveredBy;
	private String destinationLocation;
	private String originLocation;
	private String priority;
	private int woNumber;
	
	public String getContainers() {
		return containers;
	}
	
	public void setContainers(String containers) {
		this.containers = containers;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public Date getDateTimeCreated() {
		return dateTimeCreated;
	}
	
	public void setDateTimeCreated(Date dateTimeCreated) {
		this.dateTimeCreated = dateTimeCreated;
	}
	
	
	public Date getDateTimeDelivered() {
		return dateTimeDelivered;
	}
	
	public void setDateTimeDelivered(Date dateTimeDelivered) {
		this.dateTimeDelivered = dateTimeDelivered;
	}
	
	public String getDeliveredBy() {
		return deliveredBy;
	}
	
	public void setDeliveredBy(String deliveredBy) {
		this.deliveredBy = deliveredBy;
	}
	
	public String getDestinationLocation() {
		return destinationLocation;
	}
	
	public void setDestinationLocation(String destinationLocation) {
		this.destinationLocation = destinationLocation;
	}
	
	public String getOriginLocation() {
		return originLocation;
	}
	
	public void setOriginLocation(String originLocation) {
		this.originLocation = originLocation;
	}

	public String getPriority() {
		return priority;
	}
	
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	public int getWoNumber() {
		return woNumber;
	}
	
	public void setWoNumber(int woNumber) {
		this.woNumber = woNumber;
	}

}
