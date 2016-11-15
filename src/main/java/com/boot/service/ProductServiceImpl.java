package com.boot.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.boot.model.Product;
import com.boot.model.ProductReview;
import com.boot.repository.ProductRepository;
import com.boot.repository.ProductReviewRepository;

@Service
@Qualifier("productservice")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductReviewRepository productReviewRepository;

	Properties properties = new Properties();
	ClassLoader classloader = Thread.currentThread().getContextClassLoader();
	InputStream resource = classloader.getResourceAsStream("application-constants.properties");

	public ProductServiceImpl() {
		try {
			properties.load(resource);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Map<String, Integer> avgRatingMap ;
	private List<String> productIdList ;
	public List<Product> productAnalyzer(String category, String subcategory) {
		productIdList = new ArrayList<String>();
		System.out.println("In ProductService for category :"+category +" subcategory "+subcategory);
		List<Product> productList = fetchAllProductsBasedOnCriteria(category, subcategory);
		populateProductIdList(productList);
		List<ProductReview> productReviewList = fetchProductsReviewsProductSpecific(productIdList);
		findAvgProductRating(productReviewList);
		prepareProductEntityWithUpdatedRating(productList);
		persistProductEntity(productList);
		return productList;

	}

	private void populateProductIdList(List<Product> productList) {
		for(Product p : productList){
			productIdList.add(String.valueOf(p.getProductid()));
		}
	}

	private void prepareProductEntityWithUpdatedRating(List<Product> productList) {
		// TODO Auto-generated method stub
		for (Product p : productList) {
			String pid = String.valueOf(p.getProductid());
			if (avgRatingMap.containsKey(pid)) {
				p.setAvgrating(avgRatingMap.get(pid));
			}
		}
	}

	private void findAvgProductRating(List<ProductReview> productReviewList) {
		// TODO Auto-generated method stub
		avgRatingMap=new HashMap<String, Integer>();
		for (ProductReview reviews : productReviewList) {
			String productid = String.valueOf(reviews.getProduct().getProductid());
			if (avgRatingMap != null) {
				if (!avgRatingMap.containsKey(productid)) {
					avgRatingMap.put(productid, reviews.getRating());
				} else if (avgRatingMap.containsKey(productid)) {
					avgRatingMap.put(productid, (reviews.getRating() + avgRatingMap.get(productid)) / 2);
					
				}
			}
		}
	}

	@Override
	public List<ProductReview> fetchAllReviews() {
		System.out.println("Fetching all products reviews");
		return productReviewRepository.findAllReviews();
	}

	
	@Override
	public List<Product> fetchAllProductsBasedOnCriteria(String category, String subcategory) {
		// TODO Auto-generated method stub
		List<Product> productList = productRepository.getProductsIgnoreCase(category, subcategory);
		return productList;
	}

	@Override
	public void persistProductEntity(List<Product> productList) {
		// TODO Auto-generated method stub
		for (Product p : productList) {
			productRepository.save(p);
		}
	}

	@Override
	public List<Product> fetchAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductReview> fetchProductsReviewsProductSpecific(List<String> productidlist) {
		// TODO Auto-generated method stub
		System.out.println("Fetching all products reviews");
		return productReviewRepository.getProductsReviewsProductSpecific(productidlist);
	}

}
