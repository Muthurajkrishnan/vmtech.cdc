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
import com.boot.model.ProductReview;
import com.boot.model.Vendor;
import com.boot.repository.ConsumerPriceRepository;
import com.boot.repository.ProductRepository;
import com.boot.repository.ProductReviewRepository;
import com.boot.repository.VendorRepository;

@Service
public class VendorService {

	Properties properties = new Properties();
	ClassLoader classloader = Thread.currentThread().getContextClassLoader();
	InputStream resource = classloader.getResourceAsStream("application-constants.properties");

	public VendorService() {
		try {
			properties.load(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<ConsumerPrice> getVendorItems(String userEmail, Vendor vendor, List<ConsumerPrice> listConsumerPrice,
			VendorRepository vendorRepository, ProductRepository productRepository,
			ConsumerPriceRepository consumerPriceRepository) {
		List<Vendor> listVendor = vendorRepository.getVendorInfoByVendorEmail(userEmail);
		List<Long> listProduct = new ArrayList<Long>();
		for (int i = 0; i < listVendor.size(); i++) {
			listProduct.add(listVendor.get(i).getProduct().getProductid());
		}
		listConsumerPrice = consumerPriceRepository.findByProductId(listProduct,
				properties.get("STATUS_QUOTE").toString());
		return listConsumerPrice;
	}

	public ConsumerPrice updateConsumerPrice(long consumerPriceId, String suggestedPrice, String rejectReason,
			String vendorReason, List<ConsumerPrice> listConsumerPrice, VendorRepository vendorRepository,
			ProductRepository productRepository, ConsumerPriceRepository consumerPriceRepository) {

		ConsumerPrice objConsumerPrice = consumerPriceRepository.findByConsumerPriceID(consumerPriceId);
		objConsumerPrice.setConsumerPriceID(consumerPriceId);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		java.util.Date currentDate = new java.util.Date();
		objConsumerPrice.setDateAcceptReject(currentDate);
		objConsumerPrice.setSuggestedPrice(suggestedPrice);
		objConsumerPrice.setRejectReason(rejectReason);
		objConsumerPrice.setStatus("suggested");
		if (vendorReason != null)
			objConsumerPrice.setRejectReason(vendorReason);
		return consumerPriceRepository.save(objConsumerPrice);
	}

	public ConsumerPrice approveUserPrice(long consumerPriceId, List<ConsumerPrice> listConsumerPrice,
			ConsumerPriceRepository consumerPriceRepository) {
		ConsumerPrice objConsumerPrice = consumerPriceRepository.findByConsumerPriceID(consumerPriceId);
		objConsumerPrice.setStatus(properties.get("STATUS_ACCEPTED").toString());
		java.util.Date currentDate = new java.util.Date();
		objConsumerPrice.setDateAcceptReject(currentDate);
		return consumerPriceRepository.save(objConsumerPrice);
	}

	public List<ConsumerPrice> getVendorOrders(String userEmail, List<ConsumerPrice> listConsumerPrice, Vendor vendor,
			VendorRepository vendorRepository, ConsumerPriceRepository consumerPriceRepository) {
		List<Vendor> listVendor = vendorRepository.getVendorInfoByVendorEmail(userEmail);
		List<Long> listProduct = new ArrayList<Long>();
		for (int i = 0; i < listVendor.size(); i++) {
			listProduct.add(listVendor.get(i).getProduct().getProductid());
		}
		listConsumerPrice = consumerPriceRepository.findByProductId(listProduct,
				properties.get("STATUS_ORDER").toString());
		return listConsumerPrice;
	}

	public ConsumerPrice dispatchOrder(String userEmail, long consumerPriceId, List<ConsumerPrice> listConsumerPrice,
			ConsumerPriceRepository consumerPriceRepository, ProductReviewRepository productReviewRepository) {
		ConsumerPrice objConsumerPrice = consumerPriceRepository.findByConsumerPriceID(consumerPriceId);
		objConsumerPrice.setStatus(properties.get("STATUS_DISPATCH").toString());
		java.util.Date currentDate = new java.util.Date();
		objConsumerPrice.setDateAcceptReject(currentDate);
		
		ProductReview productReview = new ProductReview();
		productReview.setProduct(objConsumerPrice.getProduct());
		productReview.setUserEmail(objConsumerPrice.getUserEmail());
		productReviewRepository.save(productReview);
		return consumerPriceRepository.save(objConsumerPrice);
	}
}
