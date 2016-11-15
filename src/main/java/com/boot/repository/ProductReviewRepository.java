package com.boot.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.boot.model.ProductReview;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
	
	@Query(value = "SELECT * FROM products_review pr where pr.product_productid = :productid", nativeQuery=true)
	public List<ProductReview> getProductsReviews(@Param("productid") String productid);
	
	@Query(value = "SELECT * FROM products_review rev where rev.user_email = :userEmail", nativeQuery=true)
	public ArrayList<ProductReview> findByUserEmail(@Param("userEmail") String userEmail);

	@Query(value = "SELECT * FROM products_review rev where rev.user_email = :userEmail and rev.orderid =:orderid and rating='-1'", nativeQuery=true)
	public ProductReview findByUserEmailAndReviewId(@Param("userEmail") String userEmail, @Param("orderid") long orderid);

	@Query(value = "update products_review set rating= :rating, comments =:comments where orderid = :orderid", nativeQuery=true)
	public ArrayList<ProductReview> updateReviews(@Param("orderid") long orderid, @Param("rating") long rating, @Param("comments") String comments);

	@Query(value = "SELECT * FROM products_review rev where rev.review_id = :reviewId", nativeQuery=true)
	public ProductReview findByReviewId(@Param("reviewId") long reviewId);
	
	@Query(value = "SELECT * FROM products_review", nativeQuery=true)
	public List<ProductReview> findAllReviews();
	
	@Query(value = "SELECT * FROM products_review pr where pr.product_productid in :productidlist", nativeQuery=true)
	public List<ProductReview> getProductsReviewsProductSpecific(@Param("productidlist") List<String> productidlist);


}
