package com.ac.careforsoul.Model;

public class User {

    private String Name;
    private String Phone;
    private String Password;
    private String IsStaff;
    private double balance;
    private String Prn;
    private String isPaid;
    private String secureCode;
    private String Gift;

    public User(String name, String phone, String password, String prn, String secureCode) {
        Name = name;
        Phone = phone;
        Password = password;
        Prn = prn;
        this.secureCode = secureCode;
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

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getIsStaff() {
        return IsStaff;
    }

    public void setIsStaff(String isStaff) {
        IsStaff = isStaff;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPrn() {
        return Prn;
    }

    public void setPrn(String prn) {
        Prn = prn;
    }

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    public String getSecureCode() {
        return secureCode;
    }

    public void setSecureCode(String secureCode) {
        this.secureCode = secureCode;
    }

    public String getGift() {
        return Gift;
    }

    public void setGift(String gift) {
        Gift = gift;
    }

    public User() {
    }
}

