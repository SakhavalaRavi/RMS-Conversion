package com.rmsConversion.RMSnew.Model;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.Type;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmsConversion.RMSnew.Utility.JsonDataUserType;

@Entity
@Table(name = "lasttrack")
public class Lasttrack {

    @Id
    @Column(name = "DeviceId")
    private Long deviceId;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "DeviceDate")
    private Date DeviceDate;

    @Column(name = "SystemDate")
    private Date SystemDate;

    @Type(value = JsonDataUserType.class)
    @Column(name = "digitaldata", columnDefinition = "json")
    private Map<String, Object> analogdigidata;

    @Type(value = JsonDataUserType.class)
    @Column(name = "gpsdata", columnDefinition = "json")
    private Map<String, Object> gpsdata;

    @Type(value = JsonDataUserType.class)
    @Column(name = "devicedata", columnDefinition = "json")
    private Map<String, Object> devicedata;

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getDeviceDate() {
        return DeviceDate;
    }

    public void setDeviceDate(Date deviceDate) {
        DeviceDate = deviceDate;
    }

    public Date getSystemDate() {
        return SystemDate;
    }

    public void setSystemDate(Date systemDate) {
        SystemDate = systemDate;
    }

    public Map<String, Object> getAnalogdigidata() {
        return analogdigidata;
    }

    public void setAnalogdigidata(Map<String, Object> analogdigidata) {
        this.analogdigidata = analogdigidata;
    }

    public Map<String, Object> getGpsdata() {
        return gpsdata;
    }

    public void setGpsdata(Map<String, Object> gpsdata) {
        this.gpsdata = gpsdata;
    }

    public Map<String, Object> getDevicedata() {
        return devicedata;
    }

    public void setDevicedata(Map<String, Object> devicedata) {
        this.devicedata = devicedata;
    }

    public Lasttrack(Long deviceId, Long userId, Date deviceDate, Date systemDate, Map<String, Object> analogdigidata,
            Map<String, Object> gpsdata) throws JsonParseException, JsonMappingException, IOException {
        super();
        this.deviceId = deviceId;
        this.userId = userId;
        DeviceDate = deviceDate;
        SystemDate = systemDate;
        this.analogdigidata = analogdigidata;
        this.gpsdata = gpsdata;
        if (this.devicedata == null)
            this.devicedata = new ObjectMapper().readValue(new JSONObject().toString(), Map.class);
    }

    public Lasttrack(Long deviceId, Long userId, Date deviceDate, Date systemDate, Map<String, Object> analogdigidata,
            Map<String, Object> gpsdata, Map<String, Object> devicedata) {
        super();
        this.deviceId = deviceId;
        this.userId = userId;
        DeviceDate = deviceDate;
        SystemDate = systemDate;
        this.analogdigidata = analogdigidata;
        this.gpsdata = gpsdata;
        this.devicedata = devicedata;
    }

    public Lasttrack() {
    }

    @Override
    public String toString() {
        return "Lasttrack [deviceId=" + deviceId + ", userId=" + userId + ", DeviceDate=" + DeviceDate + ", SystemDate="
                + SystemDate + ", analogdigidata=" + analogdigidata + ", gpsdata=" + gpsdata + ", devicedata="
                + devicedata + ", getDeviceId()=" + getDeviceId() + ", getUserId()=" + getUserId()
                + ", getDeviceDate()=" + getDeviceDate() + ", getSystemDate()=" + getSystemDate()
                + ", getAnalogdigidata()=" + getAnalogdigidata() + ", getGpsdata()=" + getGpsdata()
                + ", getDevicedata()=" + getDevicedata() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
                + ", toString()=" + super.toString() + "]";
    }
}
