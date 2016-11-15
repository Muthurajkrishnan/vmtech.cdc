package com.boot.model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
//@Document(collection = "consumer")
public class Consumer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long consumerId;
	private String consumerName;
	private String consumerEmail;
	private String consumerPassword;
	private String consumerCardInfo;
	ArrayList<Product> consumerPurchaseHistory;
	
	public Consumer(){}
	
	public long getConsumerId() {
		return consumerId;
	}
	public void setConsumerId(long consumerId) {
		this.consumerId = consumerId;
	}
	public String getConsumerName() {
		return consumerName;
	}
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}
	
	public String getConsumerEmail() {
		return consumerEmail;
	}
	public void setConsumerEmail(String consumerEmail) {
		this.consumerEmail = consumerEmail;
	}
	public String getConsumerPassword() {
		return consumerPassword;
	}
	public void setConsumerPassword(String consumerPassword) {
		this.consumerPassword = consumerPassword;
	}
	public String getConsumerCardInfo() {
		return consumerCardInfo;
	}
	public void setConsumerCardInfo(String consumerCardInfo) {
		this.consumerCardInfo = consumerCardInfo;
	}
	public ArrayList<Product> getConsumerPurchaseHistory() {
		return consumerPurchaseHistory;
	}
	public void setConsumerPurchaseHistory(ArrayList<Product> consumerPurchaseHistory) {
		this.consumerPurchaseHistory = consumerPurchaseHistory;
	}
}	