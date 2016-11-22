package com.boot.model;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;

@Entity
@Table(name="CONSUMER_SERVICE_PRICE")
//@Document(collection = "consumer_price")
public class ConsumerServicePrice implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long consumerPriceID;
	private String userEmail;
	private String userName;
	private String userPrice;
	private java.util.Date dateOfQuote;
	private java.util.Date dateAcceptReject;
	private java.util.Date dateOrder;
	private String rejectReason;
	private String suggestedPrice;
	private String status;
	private int productQuantity;
	private String vendorid;
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created")
	private java.util.Date dateCreated ;
	//String productid;
	
	private int days;
	private int hours;
		
	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getSuggestedPrice() {
		return suggestedPrice;
	}

	public void setSuggestedPrice(String suggestedPrice) {
		this.suggestedPrice = suggestedPrice;
	}

	@OneToOne(cascade = CascadeType.ALL)
	private Product product;

	public ConsumerServicePrice() {
	}

	public ConsumerServicePrice(String productid, String userEmail, String userName, String userPrice) {
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public java.util.Date getDateOfQuote() {
		return dateOfQuote;
	}

	public void setDateOfQuote(java.util.Date dateOfQuote) {
		this.dateOfQuote = dateOfQuote;
	}

	public java.util.Date getDateAcceptReject() {
		return dateAcceptReject;
	}

	public void setDateAcceptReject(java.util.Date dateAcceptReject) {
		this.dateAcceptReject = dateAcceptReject;
	}

	public long getConsumerPriceID() {
		return consumerPriceID;
	}

	public void setConsumerPriceID(long consumerPriceID) {
		this.consumerPriceID = consumerPriceID;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPrice() {
		return userPrice;
	}

	public void setUserPrice(String userPrice) {
		this.userPrice = userPrice;
	}

//	public String getProductid() {
//		return productid;
//	}
//
//	public void setProductid(String productid) {
//		this.productid = productid;
//	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getVendorid() {
		return vendorid;
	}

	public void setVendorid(String vendorid) {
		this.vendorid = vendorid;
	}

	public java.util.Date getDateOrder() {
		return dateOrder;
	}

	public void setDateOrder(java.util.Date dateOrder) {
		this.dateOrder = dateOrder;
	}

	public java.util.Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(java.util.Date dateCreated) {
		this.dateCreated = dateCreated;
	}
}
