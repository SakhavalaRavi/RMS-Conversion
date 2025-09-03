package com.rmsConversion.RMSnew.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "fencedata")
public class FenceData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long fenceid;
    
    @Column
    private String fencetype;
    
    @Column
    private String fencevalue;
    
    @Column
    private Long managerid;
    
    @Column
    private String fencename;
    
    @Column
    private Boolean status;
    
    // Getters and Setters
    public Long getFenceid() {
        return fenceid;
    }

    public void setFenceid(Long fenceid) {
        this.fenceid = fenceid;
    }

    public String getFencetype() {
        return fencetype;
    }

    public void setFencetype(String fencetype) {
        this.fencetype = fencetype;
    }

    public String getFencevalue() {
        return fencevalue;
    }

    public void setFencevalue(String fencevalue) {
        this.fencevalue = fencevalue;
    }

    public Long getManagerid() {
        return managerid;
    }

    public void setManagerid(Long managerid) {
        this.managerid = managerid;
    }

    public String getFencename() {
        return fencename;
    }

    public void setFencename(String fencename) {
        this.fencename = fencename;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
} 