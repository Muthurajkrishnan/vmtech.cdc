package com.boot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ConsumerVendorXREF {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long ConsumerVendorID;
	
	private long userID;
	private long productID;
	private long vendorID;
	private String status;
	private String rejectedReason;
	private long finalPrice;
	public long getConsumerVendorID() {
		return ConsumerVendorID;
	}
	public void setConsumerVendorID(long consumerVendorID) {
		ConsumerVendorID = consumerVendorID;
	}
	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public long getProductID() {
		return productID;
	}
	public void setProductID(long productID) {
		this.productID = productID;
	}
	public long getVendorID() {
		return vendorID;
	}
	public void setVendorID(long vendorID) {
		this.vendorID = vendorID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRejectedReason() {
		return rejectedReason;
	}
	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}
	public long getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(long finalPrice) {
		this.finalPrice = finalPrice;
	}
	
}
