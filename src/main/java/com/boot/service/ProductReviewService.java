package com.boot.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.boot.model.ProductReview;
import com.boot.repository.ProductReviewRepository;

@Service
public class ProductReviewService {
	Properties properties = new Properties();
	ClassLoader classloader = Thread.currentThread().getContextClassLoader();
	InputStream resource = classloader.getResourceAsStream("application-constants.properties");

	public ProductReviewService() {
		try {
			properties.load(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ProductReview updateReview(String userEmail, long reviewId, int rating, String reviewtitle, String comments, ProductReviewRepository productReviewRepository ){
		ProductReview productReview = productReviewRepository.findByReviewId(reviewId);
		productReview.setRating(rating);
		productReview.setReviewcomments(comments);
		productReview.setReviewtitle(reviewtitle);
		return productReviewRepository.save(productReview);
	}
}
