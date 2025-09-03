package com.rmsConversion.RMSnew.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="dashboardlayout")
public class DashBoardLayout {

	@Id
	@GeneratedValue
	@Column
	private Long dlid;

	@Column
	private String name;
	
	@Column
	private String htmlName;
	
	@Column
	private String view;
	
	@Column
	private String viewType;

	@Column
	private String profile;
	
	
	public Long getDlid() {
		return dlid;
	}

	public void setDlid(Long dlid) {
		this.dlid = dlid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHtmlName() {
		return htmlName;
	}

	public void setHtmlName(String htmlName) {
		this.htmlName = htmlName;
	}
	
	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}
}
