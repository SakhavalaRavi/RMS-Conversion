package com.rmsConversion.RMSnew.Model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "alearsummary")
public class AlearSummary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "srno")
	private Long srno;

	@Column(name = "parameterid")
	private long parameterId;

	@Column(name = "parametername")
	private String parametername;

	@Column(name = "entrytime")
	private Date entryTime;

	@Column(name = "starttime")
	private Date startTime;

	@Column(name = "endtime")
	private Date endTime = null;

	@Column(name = "duration")
	private long duration = 0;

	@Column(name = "deviceid")
	private long deviceId;

	@Column(name = "managerid")
	private long managerId;

	@Column(name = "isactive")
	private boolean isActive;

	public Long getSrno() {
		return srno;
	}

	public void setSrno(Long srno) {
		this.srno = srno;
	}

	public long getParameterId() {
		return parameterId;
	}

	public void setParameterId(long parameterId) {
		this.parameterId = parameterId;
	}

	public String getParametername() {
		return parametername;
	}

	public void setParametername(String parametername) {
		this.parametername = parametername;
	}

	public Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}

	public long getManagerId() {
		return managerId;
	}

	public void setManagerId(long managerId) {
		this.managerId = managerId;
	}

	public boolean isIsactive() {
		return isActive;
	}

	public void setIsactive(boolean isactive) {
		this.isActive = isactive;
	}
} 