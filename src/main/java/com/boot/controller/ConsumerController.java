package com.boot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boot.model.AutoSuggestion;
import com.boot.model.ConsumerPrice;
import com.boot.model.User;
import com.boot.model.Vendor;
import com.boot.repository.AutoSuggestionRepository;
import com.boot.repository.ConsumerPriceRepository;
import com.boot.repository.ProductRepository;
import com.boot.repository.ProductReviewRepository;
import com.boot.repository.UserRepository;
import com.boot.repository.VendorRepository;
import com.boot.service.UserService;
import com.boot.service.VendorService;

@RestController
public class ConsumerController {

	List<ConsumerPrice> listConsumerCart;
	List<ConsumerPrice> listVendorCart;
	UserService userService = new UserService();
	com.boot.service.ConsumerService consumerService = new com.boot.service.ConsumerService();
	VendorService vendorService = new VendorService();
	
	@Autowired
	private AutoSuggestionRepository autoSuggestionRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private VendorRepository vendorRepository;

	@Autowired
	private ConsumerPriceRepository consumerPriceRepository;
	
	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductReviewRepository productReviewRepository;
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ArrayList<User> signup(@RequestParam("userName") String userName,
			@RequestParam("userEmail") String userEmail, @RequestParam("userPassword") String userPassword,
			@RequestParam("vendorLocation") String vendorLocation, @RequestParam("serviceType") String serviceType,
			@RequestParam("proser") String proser, User user) {
		ArrayList<User> listUser = userService.saveUser(userEmail, userName, userPassword, vendorLocation, serviceType,
				proser, user, userRepository, vendorRepository, productRepository, mongoTemplate);
		return listUser;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ArrayList<User> login(@RequestParam(value = "userEmail", required = false) String userEmail,
			@RequestParam(value = "userPassword", required = false) String userPassword) {
		// consumerRepository.findByConsumcterEmail(consumerEmail);
		System.out.println("userEmail" + userEmail);
		ArrayList<User> listUser = userService.userExists(userEmail, userPassword, userRepository);
		for (User user : listUser) {
			System.out.println(user.getUserType());
		}
		return listUser;
	}
	
	@RequestMapping(value = "/getSuggestion", method = RequestMethod.GET)
	public List<AutoSuggestion> getSuggestion() {
		return autoSuggestionRepository.findAll();
	}
	
	@RequestMapping(value = "/getProfile", method = RequestMethod.GET)
	public User getProfile(@RequestParam("userEmail") String userEmail, User user) {
		return userService.getProfile(userEmail, user, userRepository);
	}
	
	@RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
	public User updateProfile(@RequestParam("userEmail") String userEmail, @RequestParam("userName") String userName,
			@RequestParam("phoneNumber") String phoneNumber, @RequestParam("addressOne") String addressOne,
			@RequestParam("addressTwo") String addressTwo, @RequestParam("city") String city,
			@RequestParam("state") String state, @RequestParam("country") String country,
			@RequestParam("zipcode") String zipcode, User user) {
		System.out.println(userEmail + phoneNumber + addressOne + addressTwo + city);
		return userService.udpateProfile(userEmail, userName, phoneNumber, addressOne, addressTwo, city, state, country,
				zipcode, user, userRepository);
	}

	@RequestMapping(value = "/submitUserQuote", method = RequestMethod.POST)
	public ArrayList<ConsumerPrice> submitUserQuote(@RequestParam("userName") String userName,
			@RequestParam("userEmail") String userEmail, @RequestParam("productID") String productID,
			@RequestParam("userPrice") String userPrice, @RequestParam("quantity") int quantity,
			ConsumerPrice consumerPrice) {
		return consumerService.saveConsumerPrice(userName, userEmail, productID, userPrice, quantity, consumerPrice,
				consumerPriceRepository, productRepository);
	}

	@RequestMapping(value = "/getConsumerCart", method = RequestMethod.GET)
	public List<ConsumerPrice> getConsumerCart(@RequestParam("userEmail") String userEmail,
			ConsumerPrice consumerPrice) {
		return consumerService.getPendingCart(userEmail, consumerPrice, consumerPriceRepository);
	}

	@RequestMapping(value = "/deleteCart", method = RequestMethod.POST)
	public List<ConsumerPrice> deleteCart(@RequestParam("consumerPrice") long consumerPrice,
			@RequestParam("userEmail") String userEmail, ConsumerPrice objConsumerPrice) {
		consumerService.deleteCart(consumerPrice, consumerPriceRepository);
		return consumerService.getPendingCart(userEmail, objConsumerPrice, consumerPriceRepository);
	}

	@RequestMapping(value = "/placeQuote", method = RequestMethod.POST)
	public ArrayList<ConsumerPrice> placeQuote(@RequestBody List<ConsumerPrice> updatedItems,
			ConsumerPrice consumerPrice) {
		return consumerService.placeQuote(updatedItems, consumerPrice, consumerPriceRepository);
	}

	@RequestMapping(value = "/updateQuotes", method = RequestMethod.POST)
	public List<ConsumerPrice> updateQuotes(@RequestBody List<ConsumerPrice> updatedItems,
			List<ConsumerPrice> listConsumerPrice) {
		System.out.println(updatedItems);
		return consumerService.updateQuotes(updatedItems, consumerPriceRepository);
	}

	@RequestMapping(value = "/deleteQuote", method = RequestMethod.POST)
	public List<ConsumerPrice> deleteQuote(@RequestParam("consumerPrice") long consumerPrice,
			@RequestParam("userEmail") String userEmail, List<ConsumerPrice> listConsumerPrice) {
		consumerService.deleteQuote(consumerPrice, consumerPriceRepository);
		return consumerService.getUserQuotes(userEmail, consumerPriceRepository);
	}

	@RequestMapping(value = "/getUserQuotes", method = RequestMethod.GET)
	public List<ConsumerPrice> getUserQuotes(@RequestParam("userEmail") String userEmail, ConsumerPrice consumerPrice) {
		return consumerService.getUserQuotes(userEmail, consumerPriceRepository);
	}

	@RequestMapping(value = "/getOrderByUser", method = RequestMethod.GET)
	public List<ConsumerPrice> getOrderByUser(@RequestParam("userEmail") String userEmail, ConsumerPrice consumerPrice,
			List<ConsumerPrice> listConsumerPrice) {
		return consumerService.getOrderByUser(userEmail, listConsumerPrice, consumerPriceRepository);
	}

	@RequestMapping(value = "/getVendorAcceptedQuotes", method = RequestMethod.GET)
	public List<ConsumerPrice> getVendorAcceptedQuotes(@RequestParam("userEmail") String userEmail,
			ConsumerPrice consumerPrice) {
		listConsumerCart = consumerService.getVendorAcceptedQuotes(userEmail, consumerPriceRepository);
		return listConsumerCart;
	}

	@RequestMapping(value = "/getVendorCart", method = RequestMethod.GET)
	public List<ConsumerPrice> getVendorCart(@RequestParam("userEmail") String userEmail, Vendor vendor,
			List<ConsumerPrice> listConsumerPrice) {
		return vendorService.getVendorItems(userEmail, vendor, listConsumerPrice, vendorRepository, productRepository,
				consumerPriceRepository);
	}

	@RequestMapping(value = "/rejectUserPrice", method = RequestMethod.POST)
	public ConsumerPrice rejectUserPrice(@RequestParam("consumerPriceId") long consumerPriceId,
			@RequestParam("suggestedPrice") String suggestedPrice, @RequestParam("rejectReason") String rejectReason,
			@RequestParam("vendorReason") String vendorReason, Vendor vendor, List<ConsumerPrice> listConsumerPrice) {
		return vendorService.updateConsumerPrice(consumerPriceId, suggestedPrice, rejectReason, vendorReason,
				listConsumerPrice, vendorRepository, productRepository, consumerPriceRepository);
	}

	@RequestMapping(value = "/approveUserPrice", method = RequestMethod.POST)
	public ConsumerPrice approveUserPrice(@RequestParam("consumerPriceId") long consumerPriceId, Vendor vendor,
			List<ConsumerPrice> listConsumerPrice) {
		return vendorService.approveUserPrice(consumerPriceId, listConsumerPrice, consumerPriceRepository);
	}

	@RequestMapping(value = "/getVendorOrders", method = RequestMethod.GET)
	public List<ConsumerPrice> getVendorOrders(@RequestParam("userEmail") String userEmail, Vendor vendor,
			List<ConsumerPrice> listConsumerPrice) {
		return vendorService.getVendorOrders(userEmail, listConsumerPrice, vendor, vendorRepository,
				consumerPriceRepository);
	}

	@RequestMapping(value = "/placeOrder", method = RequestMethod.POST)
	public ConsumerPrice placeOrder(@RequestParam("consumerPriceID") long consumerPriceID) {
		return consumerService.placeOrder(consumerPriceID, consumerPriceRepository);
	}

	@RequestMapping(value = "/dispatchOrder", method = RequestMethod.POST)
	public ConsumerPrice dispatchOrder(@RequestParam("userEmail") String userEmail, @RequestParam("consumerPriceID") long consumerPriceID,
			List<ConsumerPrice> listConsumerPrice) {
		return vendorService.dispatchOrder(userEmail, consumerPriceID, listConsumerPrice, consumerPriceRepository, productReviewRepository);
	}

	// @RequestMapping(value="/listProducts", method = RequestMethod.GET)
	// public ArrayList<Product> listProducts(){
	// return null;
	// }

	@RequestMapping(value = "/cdc/error", method = RequestMethod.GET)
	public String home() {
		System.out.println("Error");
		return "home";
	}

}
