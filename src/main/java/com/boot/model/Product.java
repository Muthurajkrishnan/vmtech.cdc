package com.boot.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
// @Document(collection = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long productid;
	private String productName;
	private long productPrice;
	private String productDescription;
	private String productStock;
	private long productSoldQty;
	private long productMinPrice;
	private long productMaxPrice;
	private String productCategory;
	private String productSubCategory;
	private String productItemCategory;
	private String productBrand;
	private String productVersion;
	private long productAvailableQty;
	private String productImage;
	private int avgrating;
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created")
	private java.util.Date dateCreated;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Vendor> listVendor;

	// private ConsumerPrice consumerPrice;

	public long getProductAvailableQty() {
		return productAvailableQty;
	}

	public void setProductAvailableQty(long productAvailableQty) {
		this.productAvailableQty = productAvailableQty;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public long getProductid() {
		return productid;
	}

	public void setProductid(long productid) {
		this.productid = productid;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public long getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(long productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getProductStock() {
		return productStock;
	}

	public void setProductStock(String productStock) {
		this.productStock = productStock;
	}

	public long getProductSoldQty() {
		return productSoldQty;
	}

	public void setProductSoldQty(long productSoldQty) {
		this.productSoldQty = productSoldQty;
	}

	public long getProductMinPrice() {
		return productMinPrice;
	}

	public void setProductMinPrice(long productMinPrice) {
		this.productMinPrice = productMinPrice;
	}

	public long getProductMaxPrice() {
		return productMaxPrice;
	}

	public void setProductMaxPrice(long productMaxPrice) {
		this.productMaxPrice = productMaxPrice;
	}

	public String getProductSubCategory() {
		return productSubCategory;
	}

	public void setProductSubCategory(String productSubCategory) {
		this.productSubCategory = productSubCategory;
	}

	public List<Vendor> getListVendor() {
		return listVendor;
	}

	public void setAvgrating(int avgrating) {
		this.avgrating = avgrating;
	}

	public long getAvgrating() {
		return avgrating;
	}

	public void setListVendor(List<Vendor> listVendor) {
		this.listVendor = listVendor;
	}

	public java.util.Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(java.util.Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getProductItemCategory() {
		return productItemCategory;
	}

	public void setProductItemCategory(String productItemCategory) {
		this.productItemCategory = productItemCategory;
	}

	public String getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}

	public String getProductVersion() {
		return productVersion;
	}

	public void setProductVersion(String productVersion) {
		this.productVersion = productVersion;
	}

}
