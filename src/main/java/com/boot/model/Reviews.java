//package com.boot.model;
//
//import java.util.List;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.OneToMany;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//
//import org.springframework.data.mongodb.core.mapping.Document;
//
//@Entity
////@Document(collection = "reviews")
//public class Reviews {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	long orderid;
//	
//	String useremail;
//	String productid;
//	String productimage;
//	String productname;
//	String productcategory;
//	String productsubcategory;
//	String comments;
//	long rating;
//	
//	public long getOrderid() {
//		return orderid;
//	}
//	public void setOrderid(long orderid) {
//		this.orderid = orderid;
//	}
//	public String getUseremail() {
//		return useremail;
//	}
//	public void setUseremail(String useremail) {
//		this.useremail = useremail;
//	}
//	public String getProductid() {
//		return productid;
//	}
//	public void setProductid(String productid) {
//		this.productid = productid;
//	}
//	public String getProductimage() {
//		return productimage;
//	}
//	public void setProductimage(String productimage) {
//		this.productimage = productimage;
//	}
//	public String getProductname() {
//		return productname;
//	}
//	public void setProductname(String productname) {
//		this.productname = productname;
//	}
//	public String getProductcategory() {
//		return productcategory;
//	}
//	public void setProductcategory(String productcategory) {
//		this.productcategory = productcategory;
//	}
//	public String getProductsubcategory() {
//		return productsubcategory;
//	}
//	public void setProductsubcategory(String productsubcategory) {
//		this.productsubcategory = productsubcategory;
//	}
//	public String getComments() {
//		return comments;
//	}
//	public void setComments(String comments) {
//		this.comments = comments;
//	}
//	public long getRating() {
//		return rating;
//	}
//	public void setRating(long rating) {
//		this.rating = rating;
//	}
//}
