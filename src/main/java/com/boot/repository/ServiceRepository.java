package com.boot.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.boot.model.Product;
import com.boot.model.Vendor;

@Repository
public interface ServiceRepository extends JpaRepository<Product, Long> {

	//@Query(value = "SELECT new ConsumerPrice(cp.productid, cp.userEmail, cp.userName, cp.userPrice) FROM ConsumerPrice cp where cp.status = 'cart'", nativeQuery=true)
	@Query(value = "SELECT * FROM reviews rev where rev.userEmail = :userEmail", nativeQuery=true)
	ArrayList<Product> findByUserEmail(@Param("userEmail") String userEmail);
	
	@Query(value = "SELECT * FROM Product ser where ser.product_category = 'SERVICES'", nativeQuery=true)
	ArrayList<Product> findAllCatagories();

	

	@Query(value = "update reviews set rating= :rating, comments =:comments where orderid = :orderid", nativeQuery=true)
	//@Query(value = "SELECT * FROM reviews rev where rev.userEmail = :userEmail and rev.orderid =:orderid and rating='-1'", nativeQuery=true)
	ArrayList<Product> updateReviews(@Param("orderid") long orderid, @Param("rating") long rating, @Param("comments") String comments);
	
	
}
