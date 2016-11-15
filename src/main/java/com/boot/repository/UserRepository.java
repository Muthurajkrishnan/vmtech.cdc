package com.boot.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boot.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>  {
	
	public ArrayList<User> findByuserEmail(String userEmail);
}
