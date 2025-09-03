package com.rmsConversion.RMSnew.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "componet")
public class Componet {

	@Id
	@GeneratedValue
	@Column
	private Long id;
	
	@Column
	private String componet;
	
	@Column
	private String unit;
	
	@Column
	private String image;

	public String getComponet() {
		return componet;
	}

	public void setComponet(String componet) {
		this.componet = componet;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
