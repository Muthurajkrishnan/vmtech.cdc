package com.boot.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boot.model.Product;
import com.boot.model.ProductReview;
import com.boot.repository.ProductRepository;
import com.boot.repository.ProductReviewRepository;
import com.boot.service.ProductService;

@RestController
public class ProductController {
	
//	@RequestMapping(value = "/cdc", method = RequestMethod.GET)
//	public String index() {
//		System.out.println("index");
//		return "index";
//	}
//	
//	@RequestMapping(value = "/cdc/home", method = RequestMethod.GET)
//	public String home() {
//		System.out.println("homeeee");
//		return "home";
//	}

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductReviewRepository reviewRepository;
	
	@Autowired
	private ProductService productService; 
	
	
	@RequestMapping(value = "/getProductReviews", method = RequestMethod.GET)
	private List<ProductReview> getProductReviews(@RequestParam("productid") String productid) {
		System.out.println("getProductReviews");
		return reviewRepository.getProductsReviews(productid);
	}
	
	@RequestMapping(value = "/getProducts", method = RequestMethod.GET)
	private java.util.List<Product> getProducts(@RequestParam("category") String category, @RequestParam("subcategory") String subcategory) {
		System.out.println("getElectronicProducts");
		return productService.productAnalyzer(category, subcategory);
	}
	
	@RequestMapping(value = "/getProductList", method = RequestMethod.GET)
	private java.util.List<Product> getProductList() {
		return productRepository.findAllByGroup();
	}
	
	@RequestMapping(value = "/findAllByPartialSearch", method = RequestMethod.GET)
	private java.util.List<Product> findAllByPartialSearch(@RequestParam("partialSearchText") String partialSearchText) {
		return productRepository.findAllByPartialSearch(partialSearchText);
	}
	
//	@RequestMapping(value = "/listProducts", method = RequestMethod.GET)
//	private java.util.List<Product> listProducts() {
//		System.out.println("List all");
//		return productRepository.findAll();
//	}

	@RequestMapping(value = "saveProducts", method = RequestMethod.POST)
	private Product saveProducts(@RequestBody Product product) {
		return productRepository.save(product);
	}

	@RequestMapping(value = "productByID", method = RequestMethod.PUT)
	private Product getProductById(@PathVariable Long id) {
		return productRepository.findOne(id);
	}

	@RequestMapping(value = "updateProduct")
	public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
		Product existingProduct = productRepository.findOne(id);
		BeanUtils.copyProperties(product, existingProduct);
		return productRepository.saveAndFlush(existingProduct);
	}
}
