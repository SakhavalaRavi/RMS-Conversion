package com.rmsConversion.RMSnew.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "poiData")
public class PoiData {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
	private Long poiId;
	
	@Column
	private String Address;
	
	@Column
	private Double longitude;
	
	@Column
	private Double Latitude;
	
	@Column
	private String location;
	
	@Column
	private Boolean status;
	
	@Column
	private Long managerId;

	public Long getPoiId() {
		return poiId;
	}

	public void setPoiId(Long poiId) {
		this.poiId = poiId;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return Latitude;
	}

	public void setLatitude(Double latitude) {
		Latitude = latitude;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}
}
