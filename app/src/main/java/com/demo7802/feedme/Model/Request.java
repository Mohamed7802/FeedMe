package com.demo7802.feedme;

import java.util.List;

public class Request {
    private String Name;
    private String Phone;
    private String Address;
    private String Total;
    private String Status;
    private List<com.demo7802.feedme.Order> orders;

    public Request(){}

    public Request(String name, String phone, String address, String total, List<com.demo7802.feedme.Order> orders) {
        Name = name;
        Phone = phone;
        Address = address;
        Total = total;
        Status = "0";   //0 is default, 1 is shipping, 2 is shipped
        this.orders = orders;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public List<com.demo7802.feedme.Order> getOrders() {
        return orders;
    }

    public void setOrders(List<com.demo7802.feedme.Order> orders) {
        this.orders = orders;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
