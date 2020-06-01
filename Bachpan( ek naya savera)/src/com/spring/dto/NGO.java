package com.spring.dto;

import java.io.Serializable;


public class NGO implements Serializable
{
    private int id;
    String name;
    String desc;
    String link;
    String need;
    String address;
    String phone;

    public NGO() {
    }

    public NGO(int id, String name, String desc, String link, String need, String address, String phone) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.link = link;
        this.need = need;
        this.address = address;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNeed() {
        return need;
    }

    public void setNeed(String need) {
        this.need = need;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return name;
    }
}
