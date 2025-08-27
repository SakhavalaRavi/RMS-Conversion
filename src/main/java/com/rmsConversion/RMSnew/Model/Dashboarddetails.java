package com.rmsConversion.RMSnew.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="dashboarddetails")
public class Dashboarddetails {
	
	@Id
	@Column
	@GeneratedValue
	private Long id;
	
	@Column
	private String dashboardname;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDashboardname() {
		return dashboardname;
	}

	public void setDashboardname(String dashboardname) {
		this.dashboardname = dashboardname;
	}

	

}
