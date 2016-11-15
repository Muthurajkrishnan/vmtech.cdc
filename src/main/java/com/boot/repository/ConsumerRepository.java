package com.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boot.model.Consumer;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Long> {
	public Consumer findByConsumerEmail(String consumerEmail);
}
