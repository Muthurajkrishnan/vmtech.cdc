package com.boot.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="PRODUCTS_REVIEW")
public class ProductReview {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long reviewId;
	@ManyToOne(cascade = CascadeType.ALL)
	private Product product;
	private String reviewcomments;
	private String reviewby;
	private String reviewtitle;
	private int rating;
	private String userEmail;
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created")
	private java.util.Date dateCreated ;
	
	public long getReviewId() {
		return reviewId;
	}
	public void setReviewId(long reviewId) {
		this.reviewId = reviewId;
	}
	public String getReviewcomments() {
		return reviewcomments;
	}
	public void setReviewcomments(String reviewcomments) {
		this.reviewcomments = reviewcomments;
	}
	public String getReviewby() {
		return reviewby;
	}
	public void setReviewby(String reviewby) {
		this.reviewby = reviewby;
	}
	public String getReviewtitle() {
		return reviewtitle;
	}
	public void setReviewtitle(String reviewtitle) {
		this.reviewtitle = reviewtitle;
	}
	
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public java.util.Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(java.util.Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
}
