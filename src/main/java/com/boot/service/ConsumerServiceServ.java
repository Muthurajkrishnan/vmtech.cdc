package com.boot.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.boot.model.ConsumerPrice;
import com.boot.model.ConsumerServicePrice;
import com.boot.model.Product;
import com.boot.repository.ConsumerServicePriceRepository;
import com.boot.repository.ConsumerRepository;
import com.boot.repository.ConsumerServicePriceRepository;
import com.boot.repository.ProductRepository;

@Service
public class ConsumerServiceServ {

	Properties properties = new Properties();
	ClassLoader classloader = Thread.currentThread().getContextClassLoader();
	InputStream resource = classloader.getResourceAsStream("application-constants.properties");

	public ConsumerServiceServ() {
		try {
			properties.load(resource);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<ConsumerServicePrice> saveConsumerPrice(String userName, String userEmail, String productID,
			String userPrice, int days,int hours, ConsumerServicePrice consumerServicePrice,
			ConsumerServicePriceRepository consumerServicePriceRepository, ProductRepository productRepository) {
		ArrayList<ConsumerServicePrice> listConsumerPrice = new ArrayList<ConsumerServicePrice>();
		consumerServicePrice.setUserName(userName);
		consumerServicePrice.setUserEmail(userEmail);
		consumerServicePrice.setUserPrice(userPrice);
		
		consumerServicePrice.setDays(days);
		consumerServicePrice.setHours(hours);
		
		consumerServicePrice.setStatus("cart");
		Product product = new Product();
		product = productRepository.getProductInfoByID(productID);
		consumerServicePrice.setProduct(product);
		listConsumerPrice.add(consumerServicePriceRepository.save(consumerServicePrice));
		return listConsumerPrice;
	}

	public List<ConsumerServicePrice> getPendingCart(String userEmail, ConsumerServicePrice consumerServicePrice,
			ConsumerServicePriceRepository consumerServicePriceRepository) {
		return consumerServicePriceRepository.findByUserEmail(userEmail, properties.get("STATUS_CART").toString());
	}
	
	public void deleteCart(long consumerServicePriceID, 
			ConsumerServicePriceRepository consumerServicePriceRepository) {
		consumerServicePriceRepository.deleteByConsumerPriceID(consumerServicePriceID);
	}

	public ArrayList<ConsumerServicePrice> placeServiceQuote(List<ConsumerServicePrice> updatedItems,
			ConsumerServicePrice consumerServicePrice, ConsumerServicePriceRepository consumerServicePriceRepository) {
		ArrayList<ConsumerServicePrice> listConsumerPrice = new ArrayList<ConsumerServicePrice>();

		System.out.println(updatedItems);
		Iterator<ConsumerServicePrice> iterator = updatedItems.iterator();
		String userEmail = null;
		while (iterator.hasNext()) {
			ConsumerServicePrice objConsumerPrice = (ConsumerServicePrice) iterator.next();
			objConsumerPrice.setStatus("quoted");
			userEmail = objConsumerPrice.getUserEmail();
			objConsumerPrice.setDateOfQuote(new Date());
			consumerServicePriceRepository.save(objConsumerPrice);
		}

		listConsumerPrice = consumerServicePriceRepository.findByUserEmail(userEmail,
				properties.get("STATUS_QUOTE").toString());
		return listConsumerPrice;
	}

	public List<ConsumerServicePrice> getUserQuotes(String userEmail, ConsumerServicePriceRepository consumerServicePriceRepository) {
		try {
			InputStream resource = classloader.getResourceAsStream("application-constants.properties");
			// in = new FileInputStream("application-constants.properties");
			properties.load(resource);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<String> listStatus = new ArrayList<String>();
		String status = properties.get("STATUS_QUOTE").toString();
		String statusSuggested = properties.get("STATUS_SUGGESTED").toString();
		listStatus.add(status);
		listStatus.add(statusSuggested);
		return consumerServicePriceRepository.getUserQuotesByuserEmail(userEmail, listStatus);
	}

	public List<ConsumerServicePrice> updateQuotes(List<ConsumerServicePrice> listConsumerPrice,
			ConsumerServicePriceRepository consumerServicePriceRepository) {
		for (ConsumerServicePrice consumerServicePrice : listConsumerPrice) {
			consumerServicePrice.setStatus(properties.get("STATUS_QUOTE").toString());
			consumerServicePriceRepository.save(consumerServicePrice);
		}
		return listConsumerPrice;
	}
	
	public void deleteQuote(long consumerServicePriceID, ConsumerServicePriceRepository consumerServicePriceRepository){
		consumerServicePriceRepository.deleteByConsumerPriceID(consumerServicePriceID);
	}
	
	public List<ConsumerServicePrice> getOrderByUser(String userEmail, List<ConsumerServicePrice> listConsumerPrice, ConsumerServicePriceRepository consumerServicePriceRepository){
		List<String> listStatus = new ArrayList<String>();
		String status = properties.get("STATUS_ORDER").toString();
		String statusSuggested = properties.get("STATUS_DISPATCH").toString();
		listStatus.add(status);
		listStatus.add(statusSuggested);
		return consumerServicePriceRepository.findOrdersByUserEmail(userEmail, listStatus);
	}

	public List<ConsumerServicePrice> getVendorAcceptedQuotes(String userEmail,
			ConsumerServicePriceRepository consumerServicePriceRepository) {
		String statusSuggested = properties.get("STATUS_ACCEPTED").toString();
		return consumerServicePriceRepository.getVendorAcceptedQuotesByuserEmail(userEmail, statusSuggested);
	}
	
	public ConsumerServicePrice placeOrder(long consumerServicePriceID, ConsumerServicePriceRepository consumerServicePriceRepository) {
		ConsumerServicePrice consumerServicePrice =  consumerServicePriceRepository.findByConsumerPriceID(consumerServicePriceID);
		consumerServicePrice.setStatus(properties.get("STATUS_ORDER").toString());
		consumerServicePrice.setDateOfQuote(new Date());
		return consumerServicePriceRepository.save(consumerServicePrice);
	}
}
