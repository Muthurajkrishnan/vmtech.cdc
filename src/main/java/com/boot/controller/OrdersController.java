package com.boot.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boot.model.ProductReview;
import com.boot.repository.ProductReviewRepository;
import com.boot.service.ProductReviewService;

@RestController
public class OrdersController {

	ProductReviewService productReviewService = new ProductReviewService();
	@Autowired
	private ProductReviewRepository productReviewRepository;

	@RequestMapping(value = "/getOrders", method = RequestMethod.GET)
	private java.util.List<ProductReview> getOrders(@RequestParam("userEmail") String userEmail) {
		System.out.println("getLoggedinUser - userEmail" + userEmail);
		ArrayList ar = new ArrayList();
		ar = productReviewRepository.findByUserEmail(userEmail);
		System.out.println(ar.size());
		return ar;
	}

	// @RequestMapping(value = "/getOrderDetails", method = RequestMethod.GET)
	// private java.util.List<ProductReview>
	// getOrderDetails(@RequestParam("userEmail") String userEmail,
	// @RequestParam("reviewId") long reviewId) {
	// ArrayList<ProductReview> ar = new ArrayList<ProductReview>();
	// ar = reviewsRepository.findByUserEmailAndReviewId(userEmail,reviewId);
	// return ar;
	// }

	@RequestMapping(value = "/updateReviews", method = RequestMethod.POST)
	private ProductReview updateReviews(@RequestParam("userEmail") String userEmail,
			@RequestParam("reviewId") long reviewId, @RequestParam("rating") int rating,
			@RequestParam("comments") String comments, @RequestParam("reviewtitle") String reviewTitle) {

		ArrayList<ProductReview> ar = new ArrayList<ProductReview>();
		return productReviewService.updateReview(userEmail, reviewId, rating, reviewTitle, comments, productReviewRepository);
	}

}
