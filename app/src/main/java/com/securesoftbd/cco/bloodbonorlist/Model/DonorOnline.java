package com.securesoftbd.cco.bloodbonorlist.Model;

public class DonorOnline {
    private String id;
    private String name;
    private String age;
    private String image;
    private String bloodGroup;
    private String countractNumber;
    private String lastDonationDate;
    private String address;

    public DonorOnline() {
    }

    public DonorOnline(String id, String name, String age, String image, String bloodGroup, String countractNumber, String lastDonationDate, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.image = image;
        this.bloodGroup = bloodGroup;
        this.countractNumber = countractNumber;
        this.lastDonationDate = lastDonationDate;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getCountractNumber() {
        return countractNumber;
    }

    public void setCountractNumber(String countractNumber) {
        this.countractNumber = countractNumber;
    }

    public String getLastDonationDate() {
        return lastDonationDate;
    }

    public void setLastDonationDate(String lastDonationDate) {
        this.lastDonationDate = lastDonationDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
