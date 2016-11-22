package com.boot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boot.model.Product;
import com.boot.model.Vendor;
import com.boot.repository.ServiceRepository;
import com.boot.repository.VendorRepository;

@RestController
public class ServiceController {
	
	@Autowired
	private ServiceRepository serviceRepository;
	@Autowired
	private VendorRepository vendorRepository;


	@RequestMapping(value = "/createServiceOrders", method = RequestMethod.GET)
	private java.util.List<Product> createServiceOrders() {
		System.out.println("createServiceOrders-getLoggedinUser!!!!!");
		ArrayList ar = new ArrayList();
		ar = serviceRepository.findAllCatagories();
		System.out.println(ar.size());
		return ar;
	}
	
	@RequestMapping(value = "/selectVendor", method = RequestMethod.GET)
	private java.util.List<Vendor> selectVendor(@RequestParam("catselected") String catselected) {
		System.out.println("selectVendor-getLoggedinUser !!!" );
		System.out.println("selectVendor-getLoggedinUser - categorySelected" +catselected);
		List<Vendor> ar = new ArrayList();
		ar = vendorRepository.findVendorByCatagoriesAndLocation(catselected);
		System.out.println(ar.size());
		return ar;
	}
	
	@RequestMapping(value = "/selectServiceLocations", method = RequestMethod.GET)
	private java.util.List<Vendor> selectServiceLocations(@RequestParam("catselected") String catselected, @RequestParam("vendor") String vendor) {
		System.out.println("selectServiceLocations-getLoggedinUser!!!!");
		System.out.println("selectServiceLocations-getLoggedinUser - categorySelected" +catselected);
		System.out.println("selectServiceLocations-getLoggedinUser - vendor" +vendor);
		List<Vendor> ar = new ArrayList();
		ar = vendorRepository.findLocationByCatagories(catselected, vendor);
		System.out.println(ar.size());
		return ar;
	}
	
	

	
}
