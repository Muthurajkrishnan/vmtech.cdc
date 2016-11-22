package com.boot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class AutoSuggestion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long suggesterID;
	private String suggestionString;
	private String suggestionCategory;
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created")
	private java.util.Date dateCreated ;
	
	public long getSuggesterID() {
		return suggesterID;
	}
	public void setSuggesterID(long suggesterID) {
		this.suggesterID = suggesterID;
	}
	public String getSuggestionString() {
		return suggestionString;
	}
	public void setSuggestionString(String suggestionString) {
		this.suggestionString = suggestionString;
	}
	public String getSuggestionCategory() {
		return suggestionCategory;
	}
	public void setSuggestionCategory(String suggestionCategory) {
		this.suggestionCategory = suggestionCategory;
	}
	public java.util.Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(java.util.Date dateCreated) {
		this.dateCreated = dateCreated;
	}
}
