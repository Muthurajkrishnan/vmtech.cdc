package com.boot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.boot.model.Product;
import com.boot.model.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {

	@Query(value = "SELECT * FROM vendor ve where ve.vendor_email = :vendorEmail", nativeQuery = true)
	public List<Vendor> getVendorInfoByVendorEmail(@Param("vendorEmail") String vendorEmail);
	

}
