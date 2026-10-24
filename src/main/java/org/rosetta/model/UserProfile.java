package org.rosetta.model;

public class UserProfile {
    private int id;
    private int userId;
    private String phone;
    private String address;

    public UserProfile() {}

    public UserProfile(int id, int userId, String phone, String address) {
        this.id = id;
        this.userId = userId;
        this.phone = phone;
        this.address = address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "UserProfile(id: " + id + ", user_id: " + userId + ", phone: " + phone + ", address: " + address + ")";
    }
}
