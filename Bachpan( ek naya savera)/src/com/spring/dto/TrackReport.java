/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spring.dto;

import java.io.Serializable;

/**
 *
 * @author Spring
 */
public class TrackReport implements Serializable{
    String cid ;
    String rid;
    String caption;
   String action;
   String ngoName;

    public TrackReport() {
    }

    public TrackReport(String cid, String rid, String caption, String action, String ngoName) {
        this.cid = cid;
        this.rid = rid;
        this.caption = caption;
        this.action = action;
        this.ngoName = ngoName;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getNgoName() {
        return ngoName;
    }

    public void setNgoName(String ngoName) {
        this.ngoName = ngoName;
    }
   
    public String toString() {
    	// TODO Auto-generated method stub
    	return " Caption -> "+caption+":: Action -> "+action+":: NGO -> "+ngoName;
    }
   
}
