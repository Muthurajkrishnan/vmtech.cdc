package com.boot.service;

import java.util.List;

import com.boot.model.Product;
import com.boot.model.ProductReview;

public interface ProductService {
	
	public List<Product> productAnalyzer(String category, String subcategory);
	public List<ProductReview> fetchAllReviews() ;
	public List<Product> fetchAllProducts();
	public void persistProductEntity(List<Product> productList);
	List<Product> fetchAllProductsBasedOnCriteria(String category, String subcategory);
	List<ProductReview> fetchProductsReviewsProductSpecific(List<String> productidlist);

}
