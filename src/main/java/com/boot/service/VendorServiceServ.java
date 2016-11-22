package com.boot.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.boot.model.ConsumerPrice;
import com.boot.model.ConsumerServicePrice;
import com.boot.model.ProductReview;
import com.boot.model.Vendor;
import com.boot.repository.ConsumerPriceRepository;
import com.boot.repository.ConsumerServicePriceRepository;
import com.boot.repository.ProductRepository;
import com.boot.repository.ProductReviewRepository;
import com.boot.repository.VendorRepository;

@Service
public class VendorServiceServ {

	Properties properties = new Properties();
	ClassLoader classloader = Thread.currentThread().getContextClassLoader();
	InputStream resource = classloader.getResourceAsStream("application-constants.properties");

	public VendorServiceServ() {
		try {
			properties.load(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<ConsumerServicePrice> getVendorItems(String userEmail, Vendor vendor, List<ConsumerServicePrice> listConsumerPrice,
			VendorRepository vendorRepository, ProductRepository productRepository,
			ConsumerServicePriceRepository consumerServicePriceRepository) {
		List<Vendor> listVendor = vendorRepository.getVendorInfoByVendorEmail(userEmail);
		
		System.out.println("listVendor ----------------------------------::::"+listVendor.size());
		System.out.println("listVendor ----------------------------------::::"+listVendor.get(0).getVendorId());
		
		List<Long> listProduct = new ArrayList<Long>();
		for (int i = 0; i < listVendor.size(); i++) {
			listProduct.add(listVendor.get(i).getProduct().getProductid());
		}
		listConsumerPrice = consumerServicePriceRepository.findByProductId(listProduct,
				properties.get("STATUS_QUOTE").toString());
		
		System.out.println("listConsumerPrice ----------------------------------::::"+listConsumerPrice.size());
		
		return listConsumerPrice;
	}

	public ConsumerServicePrice updateConsumerPrice(long consumerPriceId, String suggestedPrice, String rejectReason,
			String vendorReason, List<ConsumerServicePrice> listConsumerPrice, VendorRepository vendorRepository,
			ProductRepository productRepository, ConsumerServicePriceRepository consumerServicePriceRepository) {

		ConsumerServicePrice objConsumerPrice = consumerServicePriceRepository.findByConsumerPriceID(consumerPriceId);
		objConsumerPrice.setConsumerPriceID(consumerPriceId);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		java.util.Date currentDate = new java.util.Date();
		objConsumerPrice.setDateAcceptReject(currentDate);
		objConsumerPrice.setSuggestedPrice(suggestedPrice);
		objConsumerPrice.setRejectReason(rejectReason);
		objConsumerPrice.setStatus("suggested");
		if (vendorReason != null)
			objConsumerPrice.setRejectReason(vendorReason);
		return consumerServicePriceRepository.save(objConsumerPrice);
	}

	public ConsumerServicePrice approveUserPrice(long consumerPriceId, List<ConsumerServicePrice> listConsumerPrice,
			ConsumerServicePriceRepository consumerServicePriceRepository) {
		ConsumerServicePrice objConsumerPrice = consumerServicePriceRepository.findByConsumerPriceID(consumerPriceId);
		objConsumerPrice.setStatus(properties.get("STATUS_ACCEPTED").toString());
		java.util.Date currentDate = new java.util.Date();
		objConsumerPrice.setDateAcceptReject(currentDate);
		return consumerServicePriceRepository.save(objConsumerPrice);
	}

	public List<ConsumerServicePrice> getVendorOrders(String userEmail, List<ConsumerServicePrice> listConsumerPrice, Vendor vendor,
			VendorRepository vendorRepository, ConsumerServicePriceRepository consumerServicePriceRepository) {
		List<Vendor> listVendor = vendorRepository.getVendorInfoByVendorEmail(userEmail);
		List<Long> listProduct = new ArrayList<Long>();
		for (int i = 0; i < listVendor.size(); i++) {
			listProduct.add(listVendor.get(i).getProduct().getProductid());
		}
		listConsumerPrice = consumerServicePriceRepository.findByProductId(listProduct,
				properties.get("STATUS_ORDER").toString());
		return listConsumerPrice;
	}

	public ConsumerServicePrice dispatchOrder(String userEmail, long consumerPriceId, List<ConsumerServicePrice> listConsumerPrice,
			ConsumerServicePriceRepository consumerServicePriceRepository, ProductReviewRepository productReviewRepository) {
		ConsumerServicePrice objConsumerPrice = consumerServicePriceRepository.findByConsumerPriceID(consumerPriceId);
		objConsumerPrice.setStatus(properties.get("STATUS_DISPATCH").toString());
		java.util.Date currentDate = new java.util.Date();
		objConsumerPrice.setDateAcceptReject(currentDate);
		
		ProductReview productReview = new ProductReview();
		productReview.setProduct(objConsumerPrice.getProduct());
		productReview.setUserEmail(objConsumerPrice.getUserEmail());
		productReviewRepository.save(productReview);
		return consumerServicePriceRepository.save(objConsumerPrice);
	}
}
