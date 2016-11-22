package com.boot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.boot.model.AutoSuggestion;

public interface AutoSuggestionRepository extends JpaRepository<AutoSuggestion, Long>  {

	@Query(value = "SELECT * FROM AUTO_SUGGESTION as where as.suggestion_category = :category", nativeQuery = true)
	List<AutoSuggestion> findBySuggestionCategory(@Param("category") String category);

}
