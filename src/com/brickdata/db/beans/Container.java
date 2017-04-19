package com.brickdata.db.beans;

import java.sql.Date;

public class Container {
	
	private String accessedby;
	private String altId;
	private String containerNumber;
	private Date createDate;
	private String customerId;
	private String description;
	private Date destroyDate;
	private Date lastAccessed;
	private String locationNumber;
	private Date nextAccess;
	private String subContainers;
	private String superContainer;
	
	public String getAccessedby() {
		return accessedby;
	}
	
	public void setAccessedby(String accessedby) {
		this.accessedby = accessedby;
	}
	
	public String getAltId() {
		return altId;
	}
	
	public void setAltId(String altId) {
		this.altId = altId;
	}

	public String getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date date) {
		this.createDate = (Date) date;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getDestroyDate() {
		return destroyDate;
	}
	
	public void setDestroyDate(Date destroyDate) {
		this.destroyDate = destroyDate;
	}

	public Date getLastAccessed() {
		return lastAccessed;
	}
	
	public void setLastAccessed(Date lastAccessed) {
		this.lastAccessed = lastAccessed;
	}
	
	public String getLocationNumber() {
		return locationNumber;
	}

	public void setLocationNumber(String locationNumber) {
		this.locationNumber = locationNumber;
	}

	public Date getNextAccess() {
		return nextAccess;
	}

	public void setNextAccess(Date nextAccess) {
		this.nextAccess = nextAccess;
	}

	public String getSubContainers() {
		return subContainers;
	}
	
	public void setSubContainers(String subContainers) {
		this.subContainers = subContainers;
	}

	public String getSuperContainer() {
		return superContainer;
	}
	
	public void setSuperContainer(String superContainer) {
		this.superContainer = superContainer;
	}
	
	

}
