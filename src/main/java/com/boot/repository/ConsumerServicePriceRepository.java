package com.boot.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.boot.model.ConsumerPrice;
import com.boot.model.ConsumerServicePrice;

@Repository
public interface ConsumerServicePriceRepository extends JpaRepository<ConsumerServicePrice, Long> {

	// CART
	@Query(value = "SELECT * FROM consumer_Service_price cp where cp.user_Email = :userEmail and cp.status = :status", nativeQuery = true)
	ArrayList<ConsumerServicePrice> findByUserEmail(@Param("userEmail") String userEmail, @Param("status") String status);

	@Query(value = "SELECT * FROM consumer_Service_price cp where cp.user_Email = :userEmail and cp.status in :listStatus", nativeQuery = true)
	List<ConsumerServicePrice> getUserQuotesByuserEmail(@Param("userEmail") String userEmail,
			@Param("listStatus") List<String> listStatus);

	@Query(value = "SELECT * FROM consumer_Service_price cp where cp.user_Email = :userEmail and cp.status = :status", nativeQuery = true)
	List<ConsumerServicePrice> getVendorAcceptedQuotesByuserEmail(@Param("userEmail") String userEmail,
			@Param("status") String status);

	@Query(value = "SELECT * FROM consumer_Service_price cp where cp.prodUCT_PRODUCTID in :productids and cp.status = :status", nativeQuery = true)
	public List<ConsumerServicePrice> findByProductId(@Param("productids") List<Long> productids,
			@Param("status") String status);

	@Query(value = "SELECT * FROM consumer_Service_price cp where cp.CONSUMER_PRICEID = :consumerPriceID", nativeQuery = true)
	public ConsumerServicePrice findByConsumerPriceID(@Param("consumerPriceID") long consumerPriceID);

	@Query(value = "SELECT * FROM consumer_Service_price cp where cp.user_Email = :userEmail and cp.status in :listStatus", nativeQuery = true)
	public List<ConsumerServicePrice> findOrdersByUserEmail(@Param("userEmail") String userEmail,
			@Param("listStatus") List<String> listStatus);
	
	@Modifying
	@Query(value = "DELETE FROM consumer_Service_price cp where cp.CONSUMER_PRICEID = :consumerPriceID", nativeQuery = true)
	@Transactional
	public void deleteByConsumerPriceID(@Param("consumerPriceID") long consumerPriceID);

}
