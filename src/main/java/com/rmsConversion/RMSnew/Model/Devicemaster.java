package com.rmsConversion.RMSnew.Model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "devicemaster")
public class Devicemaster {
	
	private Long deviceid;
	
	private String devicename;
	
	private String devicedescription; 
	
	private Boolean flagcondition;
	
	private String simcardno;
	
	private String devicemodel;
	
	private DeviceProfile dp;
	
	private String imei;
	
	private Long userId;
	
	private Long managerId;
	
	private Long sitename;
	
	private String altdevicename;

	
	@Column(name="userid_fk")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "prid_fk")
	public DeviceProfile getDp() {
		return dp;
	}
	
	public void setDp(DeviceProfile dp) {
		this.dp = dp;
	}

	@Column(name="imei")
	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	public Long getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(Long deviceid) {
		this.deviceid = deviceid;
	}
	
	@Column
	public String getDevicename() {
		return devicename;
	}

	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}
	
	@Column
	public String getDevicedescription() {
		return devicedescription;
	}

	public void setDevicedescription(String devicedescription) {
		this.devicedescription = devicedescription;
	}

	@Column
	public Boolean getFlagcondition() {
		return flagcondition;
	}

	public void setFlagcondition(Boolean flagcondition) {
		this.flagcondition = flagcondition;
	}

	@Column
	public String getSimcardno() {
		return simcardno;
	}

	public void setSimcardno(String simcardno) {
		this.simcardno = simcardno;
	}

	@Column
	public String getDevicemodel() {
		return devicemodel;
	}

	public void setDevicemodel(String devicemodel) {
		this.devicemodel = devicemodel;
	}

	@Column(name="managerId")  
	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	@Column
	public String getAltdevicename() {
		return altdevicename;
	}

	public void setAltdevicename(String altdevicename) {
		this.altdevicename = altdevicename;
	}

    public Long getSitename() {
        return sitename;
    }

    public void setSitename(Long sitename) {
        this.sitename = sitename;
    }

    
}
