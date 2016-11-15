package com.boot.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.boot.model.ConsumerPrice;

@Repository
public interface ConsumerPriceRepository extends JpaRepository<ConsumerPrice, Long> {

	// CART
	@Query(value = "SELECT * FROM consumer_price cp where cp.user_Email = :userEmail and cp.status = :status", nativeQuery = true)
	ArrayList<ConsumerPrice> findByUserEmail(@Param("userEmail") String userEmail, @Param("status") String status);

	@Query(value = "SELECT * FROM consumer_price cp where cp.user_Email = :userEmail and cp.status in :listStatus", nativeQuery = true)
	ArrayList<ConsumerPrice> getUserQuotesByuserEmail(@Param("userEmail") String userEmail,
			@Param("listStatus") List<String> listStatus);

	@Query(value = "SELECT * FROM consumer_price cp where cp.user_Email = :userEmail and cp.status = :status", nativeQuery = true)
	ArrayList<ConsumerPrice> getVendorAcceptedQuotesByuserEmail(@Param("userEmail") String userEmail,
			@Param("status") String status);

	@Query(value = "SELECT * FROM consumer_price cp where cp.prodUCT_PRODUCTID in :productids and cp.status = :status", nativeQuery = true)
	public List<ConsumerPrice> findByProductId(@Param("productids") List<Long> productids,
			@Param("status") String status);

	@Query(value = "SELECT * FROM consumer_price cp where cp.CONSUMER_PRICEID = :consumerPriceID", nativeQuery = true)
	public ConsumerPrice findByConsumerPriceID(@Param("consumerPriceID") long consumerPriceID);

	@Query(value = "SELECT * FROM consumer_price cp where cp.user_Email = :userEmail and cp.status in :listStatus", nativeQuery = true)
	public List<ConsumerPrice> findOrdersByUserEmail(@Param("userEmail") String userEmail,
			@Param("listStatus") List<String> listStatus);
	
	@Modifying
	@Query(value = "DELETE FROM consumer_price cp where cp.CONSUMER_PRICEID = :consumerPriceID", nativeQuery = true)
	@Transactional
	public void deleteByConsumerPriceID(@Param("consumerPriceID") long consumerPriceID);

}
