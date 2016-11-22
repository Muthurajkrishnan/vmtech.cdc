package com.boot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boot.model.ConsumerPrice;
import com.boot.model.ConsumerServicePrice;
import com.boot.model.Product;
import com.boot.model.User;
import com.boot.model.Vendor;
import com.boot.repository.ConsumerPriceRepository;
import com.boot.repository.ConsumerServicePriceRepository;
import com.boot.repository.ProductRepository;
import com.boot.repository.ProductReviewRepository;
import com.boot.repository.UserRepository;
import com.boot.repository.VendorRepository;
import com.boot.service.UserService;
import com.boot.service.VendorService;
import com.boot.service.VendorServiceServ;

@RestController
public class ConsumerServiceController {

	List<ConsumerServicePrice> listConsumerCart;
	List<ConsumerServicePrice> listVendorCart;
	UserService userService = new UserService();
	com.boot.service.ConsumerServiceServ consumerServiceServ = new com.boot.service.ConsumerServiceServ();
	VendorServiceServ vendorService = new VendorServiceServ();
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private VendorRepository vendorRepository;

	@Autowired
	private ConsumerServicePriceRepository consumerServicePriceRepository;

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductReviewRepository productReviewRepository;

	@RequestMapping(value = "/loginService", method = RequestMethod.POST)
	public ArrayList<User> loginService(@RequestParam(value = "userEmail", required = false) String userEmail,
			@RequestParam(value = "userPassword", required = false) String userPassword) {
		// consumerRepository.findByConsumcterEmail(consumerEmail);
		System.out.println("userEmail" + userEmail);
		ArrayList<User> listUser = userService.userExists(userEmail, userPassword, userRepository);
		for (User user : listUser) {
			System.out.println(user.getUserType());
		}
		return listUser;
	}

//	@RequestMapping(value = "/signupService", method = RequestMethod.POST)
//	public ArrayList<User> signupService(@RequestParam("userName") String userName,
//			@RequestParam("userEmail") String userEmail, @RequestParam("userPassword") String userPassword,
//			@RequestParam("vendorLocation") String vendorLocation, @RequestParam("serviceType") String serviceType,
//			@RequestParam("proser") String proser, User user) {
//		System.out.println("consumerPassword->" + userName + userEmail + "::::" + vendorLocation);
//		ArrayList<User> listUser = userService.saveUser(userEmail, userName, userPassword, vendorLocation, serviceType,
//				proser, user, userRepository, vendorRepository, productRepository);
//		return listUser;
//	}

	@RequestMapping(value = "/getProfileService", method = RequestMethod.GET)
	public User getProfileService(@RequestParam("userEmail") String userEmail, User user) {
		return userService.getProfile(userEmail, user, userRepository);
	}

	@RequestMapping(value = "/updateProfileService", method = RequestMethod.POST)
	public User updateProfileService(@RequestParam("userEmail") String userEmail, @RequestParam("userName") String userName,
			@RequestParam("phoneNumber") String phoneNumber, @RequestParam("addressOne") String addressOne,
			@RequestParam("addressTwo") String addressTwo, @RequestParam("city") String city,
			@RequestParam("state") String state, @RequestParam("country") String country,
			@RequestParam("zipcode") String zipcode, User user) {
		System.out.println(userEmail + phoneNumber + addressOne + addressTwo + city);
		return userService.udpateProfile(userEmail, userName, phoneNumber, addressOne, addressTwo, city, state, country,
				zipcode, user, userRepository);
	}

	@RequestMapping(value = "/submitServiceUserQuote", method = RequestMethod.POST)
	public ArrayList<ConsumerServicePrice> submitServiceUserQuote(@RequestParam("userName") String userName,
			@RequestParam("userEmail") String userEmail, @RequestParam("productID") String productID,
			@RequestParam("userPrice") String userPrice, @RequestParam("days") int days,@RequestParam("hours") int hours,
			ConsumerServicePrice consumerPrice) {
		System.out.println("productID :::::::::::::::::::::::::::::::::::::::::::::::::" +productID);
		return consumerServiceServ.saveConsumerPrice(userName, userEmail, productID, userPrice, days,hours, consumerPrice,
				consumerServicePriceRepository, productRepository);
	}

	@RequestMapping(value = "/getConsumerServiceCart", method = RequestMethod.GET)
	public List<ConsumerServicePrice> getConsumerServiceCart(@RequestParam("userEmail") String userEmail,
			ConsumerServicePrice consumerPrice) {
		System.out.println("userEmail *****************************************************::::::::" +userEmail);
		return consumerServiceServ.getPendingCart(userEmail, consumerPrice, consumerServicePriceRepository);
	}

	@RequestMapping(value = "/deleteServiceCart", method = RequestMethod.POST)
	public List<ConsumerServicePrice> deleteServiceCart(@RequestParam("consumerPrice") long consumerPrice,
			@RequestParam("userEmail") String userEmail, ConsumerServicePrice objConsumerPrice) {
		System.out.println("userEmail =====================================================::::::::" +consumerPrice);
		consumerServiceServ.deleteCart(consumerPrice, consumerServicePriceRepository);
		return consumerServiceServ.getPendingCart(userEmail, objConsumerPrice, consumerServicePriceRepository);
	}

	@RequestMapping(value = "/placeServiceQuote", method = RequestMethod.POST)
	public ArrayList<ConsumerServicePrice> placeServiceQuote(@RequestBody List<ConsumerServicePrice> updatedItems,
			ConsumerServicePrice consumerPrice) {
		return consumerServiceServ.placeServiceQuote(updatedItems, consumerPrice, consumerServicePriceRepository);
	}

	@RequestMapping(value = "/updateServiceQuote", method = RequestMethod.POST)
	public List<ConsumerServicePrice> updateServiceQuote(@RequestBody List<ConsumerServicePrice> updatedItems,
			List<ConsumerPrice> listConsumerPrice) {
		System.out.println(updatedItems);
		return consumerServiceServ.updateQuotes(updatedItems, consumerServicePriceRepository);
	}

	@RequestMapping(value = "/deleteServiceQuote", method = RequestMethod.POST)
	public List<ConsumerServicePrice> deleteServiceQuote(@RequestParam("consumerPrice") long consumerPrice,
			@RequestParam("userEmail") String userEmail, List<ConsumerPrice> listConsumerPrice) {
		consumerServiceServ.deleteQuote(consumerPrice, consumerServicePriceRepository);
		return consumerServiceServ.getUserQuotes(userEmail, consumerServicePriceRepository);
	}

	@RequestMapping(value = "/getServiceUserQuotes", method = RequestMethod.GET)
	public List<ConsumerServicePrice> getServiceUserQuotes(@RequestParam("userEmail") String userEmail, ConsumerPrice consumerPrice) {
		return consumerServiceServ.getUserQuotes(userEmail, consumerServicePriceRepository);
	}

	@RequestMapping(value = "/getServiceOrderByUser", method = RequestMethod.GET)
	public List<ConsumerServicePrice> getServiceOrderByUser(@RequestParam("userEmail") String userEmail, ConsumerPrice consumerPrice,
			List<ConsumerServicePrice> listConsumerPrice) {
		return consumerServiceServ.getOrderByUser(userEmail, listConsumerPrice, consumerServicePriceRepository);
	}

	@RequestMapping(value = "/getVendorAcceptedServiceQuotes", method = RequestMethod.GET)
	public List<ConsumerServicePrice> getVendorAcceptedServiceQuotes(@RequestParam("userEmail") String userEmail,
			ConsumerServicePrice consumerPrice) {
		listConsumerCart = consumerServiceServ.getVendorAcceptedQuotes(userEmail, consumerServicePriceRepository);
		return listConsumerCart;
	}

	@RequestMapping(value = "/getVendorServiceCart", method = RequestMethod.GET)
	public List<ConsumerServicePrice> getVendorServiceCart(@RequestParam("userEmail") String userEmail, Vendor vendor,
			List<ConsumerServicePrice> listConsumerPrice) {
		return vendorService.getVendorItems(userEmail, vendor, listConsumerPrice, vendorRepository, productRepository,
				consumerServicePriceRepository);
	}

	@RequestMapping(value = "/rejectUserServicePrice", method = RequestMethod.POST)
	public ConsumerServicePrice rejectUserServicePrice(@RequestParam("consumerPriceId") long consumerPriceId,
			@RequestParam("suggestedPrice") String suggestedPrice, @RequestParam("rejectReason") String rejectReason,
			@RequestParam("vendorReason") String vendorReason, Vendor vendor, List<ConsumerServicePrice> listConsumerPrice) {
		return vendorService.updateConsumerPrice(consumerPriceId, suggestedPrice, rejectReason, vendorReason,
				listConsumerPrice, vendorRepository, productRepository, consumerServicePriceRepository);
	}

	@RequestMapping(value = "/approveUserServicePrice", method = RequestMethod.POST)
	public ConsumerServicePrice approveUserServicePrice(@RequestParam("consumerPriceId") long consumerPriceId, Vendor vendor,
			List<ConsumerServicePrice> listConsumerPrice) {
		return vendorService.approveUserPrice(consumerPriceId, listConsumerPrice, consumerServicePriceRepository);
	}

	@RequestMapping(value = "/getVendorServiceOrders", method = RequestMethod.GET)
	public List<ConsumerServicePrice> getVendorServiceOrders(@RequestParam("userEmail") String userEmail, Vendor vendor,
			List<ConsumerServicePrice> listConsumerPrice) {
		return vendorService.getVendorOrders(userEmail, listConsumerPrice, vendor, vendorRepository,
				consumerServicePriceRepository);
	}

	@RequestMapping(value = "/placeServiceOrder", method = RequestMethod.POST)
	public ConsumerServicePrice placeServiceOrder(@RequestParam("consumerPriceID") long consumerPriceID) {
		return consumerServiceServ.placeOrder(consumerPriceID, consumerServicePriceRepository);
	}

	@RequestMapping(value = "/dispatchServiceOrder", method = RequestMethod.POST)
	public ConsumerServicePrice dispatchServiceOrder(@RequestParam("userEmail") String userEmail, @RequestParam("consumerPriceID") long consumerPriceID,
			List<ConsumerServicePrice> listConsumerPrice) {
		return vendorService.dispatchOrder(userEmail, consumerPriceID, listConsumerPrice, consumerServicePriceRepository, productReviewRepository);
	}

	 @RequestMapping(value="/listService", method = RequestMethod.GET)
	 public ArrayList<Product> listProducts(){
	 return null;
	 }

	/*@RequestMapping(value = "/cdc/error", method = RequestMethod.GET)
	public String home() {
		System.out.println("Error");
		return "home";
	}*/

}
